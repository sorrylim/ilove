//
//  ChatView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/15.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import FirebaseDatabase

struct ChatView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var chatList : [ChatModel] = []
    
    @State var room : ChatRoomModel? = nil
    @State var roomImage : UIImage
    @State var userId : String?
    @State var userNickname : String?
    @State var isNew : Bool
    
    
    @State var title = ""
    @State var profile : ProfileModel = ProfileModel(user_nickname: "",user_birthday: "", user_city: "", user_previewintroduce: nil)
    @State var age = 0
    
    @State var value : CGFloat=0
    
    @State var content : String = ""
    
    @State var ref : DatabaseReference?
    @State var query : DatabaseQuery?
    
    @State var menuVisible = false
    var body: some View {
        ZStack{
            VStack{
                HStack(spacing: 10){
                    Image(uiImage : roomImage)
                        .resizable()
                        .frame(width: 80, height: 80)
                        .clipShape(Circle())
                    VStack(alignment:.leading,spacing: 10){
                        Text("\(profile.user_nickname) \(age),\(profile.user_city)")
                            .font(.system(size : 12))
                        if profile.user_previewintroduce != nil {
                            Text("\(profile.user_previewintroduce!)")
                                .font(.system(size : 12))
                        }
                        else {
                            Text("")
                                .font(.system(size : 12))
                        }
                    }
                    .foregroundColor(Color.gray)
                }
                .frame(height : UIScreen.main.bounds.height*0.2)
                List{
                    ForEach(0..<chatList.count, id: \.self){i in
                        HStack{
                            if i>0 && self.chatList[i-1].chat_speaker==self.chatList[i].chat_speaker{
                                ChatRow(chat: self.chatList[i], partnerImage: self.roomImage,isContinue: true)
                            }
                            else {
                                ChatRow(chat: self.chatList[i], partnerImage: self.roomImage,isContinue: false)
                            }
                        }
                    }
                }
                .onAppear(){
                    UITableView.appearance().separatorStyle = .none
                }
                .frame(width: UIScreen.main.bounds.width, height : UIScreen.main.bounds.height*0.6)
                HStack{
                    TextField("", text: $content)
                        .lineLimit(4)
                        .font(.system(size: 15))
                    Button(action: {
                        self.sendChat()
                    }){
                        Text("send")
                    }
                }
                .frame(height : UIScreen.main.bounds.height*0.05)
                .padding(.horizontal,15)
                .offset(y: -self.value)
            }
            GeometryReader{ _ in
                HStack{
                    Spacer()
                    Menu(showing: self.presentationMode, room: self.room!)
                        .offset(x: self.menuVisible ? 0 : -UIScreen.main.bounds.width)
                        .animation(.interactiveSpring(response: 0.6, dampingFraction: 0.6, blendDuration: 0.6))
                }
            }.background(Color.black.opacity(self.menuVisible ? 0.5 : 0))
        }
        //.padding(.vertical,20)
        .onAppear(){
            
            if self.isNew == true {
                HttpService.shared.createRoomReq(userId: self.userId!, userNickname: self.userNickname!){ chatRoomModel -> Void in
                    self.room=chatRoomModel
                }
            }
            while self.room == nil {}
            
            self.ref = Database.database().reference().child("chat").child(self.room!.room_id)
            self.query = self.ref!.queryOrderedByKey()
            
            self.query!.observe(.childAdded, with: {(snapshot) -> Void in
                var key = snapshot.key
                var value = snapshot.value as! [String : Any?]
                if UserInfo.shared.ID != value["chat_speaker"].unsafelyUnwrapped as! String {
                    var map = [String:Any?]()
                    map["\(key)/unread_count"]=Int(0)
                    
                    self.ref!.updateChildValues(map)
                }
                
                var roomId=value["room_id"].unsafelyUnwrapped as! String
                var chatSpeaker=value["chat_speaker"].unsafelyUnwrapped as! String
                var chatSpeakerNickname=value["chat_speaker_nickname"].unsafelyUnwrapped as! String
                var chatContent=value["chat_content"].unsafelyUnwrapped as! String
                var chatTime=value["chat_time"].unsafelyUnwrapped as! String
                var unreadCount=value["unread_count"].unsafelyUnwrapped as! Int
                
                self.chatList.append(ChatModel(room_id: roomId, chat_speaker: chatSpeaker, chat_speaker_nickname: chatSpeakerNickname, chat_content: chatContent, chat_time: chatTime, unread_count: unreadCount))
                
                if UserInfo.shared.ID != chatSpeaker && unreadCount == 1 {
                    HttpService.shared.readChatReq(roomId: roomId, chatTime: chatTime)
                }
                
            })
            self.query!.observe(.childChanged, with: {(snapshot) -> Void in
                var value = snapshot.value as! [String : Any?]
                
                var roomId=value["room_id"].unsafelyUnwrapped as! String
                var chatSpeaker=value["chat_speaker"].unsafelyUnwrapped as! String
                var chatSpeakerNickname=value["chat_speaker_nickname"].unsafelyUnwrapped as! String
                var chatContent=value["chat_content"].unsafelyUnwrapped as! String
                var chatTime=value["chat_time"].unsafelyUnwrapped as! String
                var unreadCount=value["unread_count"].unsafelyUnwrapped as! Int
                
                for i in 0..<self.chatList.count {
                    if self.chatList[i].chat_speaker==chatSpeaker && self.chatList[i].chat_time==chatTime {
                        self.chatList[i].unread_count=0
                    }
                }
            })
            
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillShowNotification, object: nil, queue: .main) { noti in
                let value = noti.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as! CGRect
                let height = value.height
                
                self.value=height
            }
            
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: .main){ noti in
                self.value=0
            }
            if UserInfo.shared.ID == self.room!.room_maker {
                self.title = String((self.room!.room_title?.split(separator: "&")[1])!)
                
                HttpService.shared.getProfileReq(userId: self.room!.room_partner){ (profileModel) -> Void in
                    self.profile=profileModel
                    
                    var now = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                    let date=dateFormatter.string(from: now)
                    
                    var year1 = Int(date.split(separator: "-")[0])
                    var year2 = Int(profileModel.user_birthday.split(separator: "-")[0])
                    
                    self.age = year1!-year2!+1
                }
            }
            else {
                self.title = String((self.room!.room_title?.split(separator: "&")[0])!)
                
                HttpService.shared.getProfileReq(userId: self.room!.room_maker){ (profileModel) -> Void in
                    self.profile=profileModel
                    
                    var now = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                    let date=dateFormatter.string(from: now)
                    
                    var year1 = Int(date.split(separator: "-")[0])
                    var year2 = Int(profileModel.user_birthday.split(separator: "-")[0])
                    
                    self.age = year1!-year2!+1
                }
            }
        }
        .navigationBarTitle(Text("\(self.title)"))
        .navigationBarItems(trailing:
            Image(systemName: "list.dash")
                .onTapGesture {
                    self.menuVisible.toggle()
            }
        )
    }
    
    func sendChat() {
        if self.content == "" {
            return
        }
        
        let now = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let date=dateFormatter.string(from: now)
        
        var chatPartner=""
        if UserInfo.shared.ID == self.room!.room_maker {
            chatPartner=self.room!.room_partner
        }
        else {
            chatPartner=self.room!.room_maker
        }
        
        HttpService.shared.insertChatReq(roomId: self.room!.room_id, userId: UserInfo.shared.ID, userNickname: UserInfo.shared.NICKNAME, chatPartner: chatPartner, chatContent: self.content, currentDate: date){ resultModel -> Void in
            
            self.ref!.childByAutoId().setValue([
                "room_id" : self.room!.room_id,
                "chat_speaker" : UserInfo.shared.ID,
                "chat_speaker_nickname" : UserInfo.shared.NICKNAME,
                "chat_content" : self.content,
                "chat_time" : date,
                "unread_count" : 1
            ])
            self.content=""
        }
    }
}

struct ChatRow : View {
    
    var chat : ChatModel
    var partnerImage : UIImage?
    var isContinue : Bool
    
    var body: some View {
        return Group {
            if UserInfo.shared.ID == chat.chat_speaker {
                MyChat(chat: chat)
            }
            else {
                if isContinue == true {
                    ContinueChat(chat: chat)
                }
                else {
                    Chat(chat: chat, partnerImage: partnerImage!)
                }
            }
        }
    }
}

struct MyChat : View {
    
    @State var chat : ChatModel
    
    @State var unreadCount = ""
    @State var time = ""
    
    var body: some View {
        HStack{
            Spacer()
            VStack(alignment: .trailing){
                Spacer()
                if unreadCount == "읽음" {
                    Text(unreadCount)
                        .font(.system(size: 10))
                        .foregroundColor(.gray)
                }
                else {
                    Text(unreadCount)
                        .font(.system(size: 10))
                        .foregroundColor(.yellow)
                }
                Text(time)
                    .font(.system(size: 10))
                    .foregroundColor(.gray)
            }
            Text(chat.chat_content)
                .padding(5)
                .font(.system(size: 15))
                .foregroundColor(.white)
                .background(Color(red: 255/255, green: 160/255, blue: 0))
                .cornerRadius(10)
        }
        .onAppear(){
            
            if self.chat.unread_count == 0 {
                self.unreadCount="읽음"
            }
            else {
                self.unreadCount=String(self.chat.unread_count)
            }
            
            if self.chat.chat_time != nil {
                var timeSplit = self.chat.chat_time.split(separator: " ")[1].split(separator: ":")
                
                var hour = Int(timeSplit[0])
                var min = timeSplit[1]
                
                if hour! < 12 {
                    self.time = "오전 \(hour!-0) : \(min)"
                }
                else {
                    if hour != 12 {
                        self.time = "오후 \(hour!-12) : \(min)"
                    }
                    else {
                        self.time = "오후 \(hour!-0) : \(min)"
                    }
                }
            }
        }
    }
}

struct Chat : View {
    
    @State var chat : ChatModel
    
    @State var partnerImage : UIImage
    @State var unreadCount = ""
    @State var time = ""
    
    var body: some View {
        HStack{
            Image(uiImage : partnerImage)
                .resizable()
                .frame(width: 35, height: 35)
                .clipShape(Circle())
            
            VStack(alignment: .leading){
                Text(chat.chat_speaker_nickname)
                    .font(.system(size: 12))
                    .foregroundColor(.gray)
                if UserInfo.shared.MESSAGETICKET == 0 {
                    HStack{
                        Text(chat.chat_content)
                            .padding(5)
                            .font(.system(size: 15))
                            .foregroundColor(.black)
                            .background(Color(red: 230/255, green: 230/255, blue: 230/255))
                            .cornerRadius(10)
                            .blur(radius: 5)
                        
                        Text("확인하기")
                            .padding(10)
                            .font(.system(size: 15))
                            .foregroundColor(.white)
                            .background(LinearGradient(gradient: Gradient(colors: [Color(red: 255/255, green: 105/255, blue: 180/255), Color(red: 255/255, green: 176/255, blue: 244/255)]), startPoint: .leading, endPoint: .trailing))
                            .cornerRadius(15)
                    }
                }
                else {
                    Text(chat.chat_content)
                        .padding(5)
                        .font(.system(size: 15))
                        .foregroundColor(.black)
                        .background(Color(red: 230/255, green: 230/255, blue: 230/255))
                        .cornerRadius(10)
                }
            }
            VStack{
                Spacer()
                Text(time)
                    .font(.system(size: 10))
                    .foregroundColor(.gray)
            }
            Spacer()
        }
        .onAppear(){
            
            if self.chat.chat_time != nil {
                var timeSplit = self.chat.chat_time.split(separator: " ")[1].split(separator: ":")
                
                var hour = Int(timeSplit[0])
                var min = timeSplit[1]
                
                if hour! < 12 {
                    self.time = "오전 \(hour!-0) : \(min)"
                }
                else {
                    if hour != 12 {
                        self.time = "오후 \(hour!-12) : \(min)"
                    }
                    else {
                        self.time = "오후 \(hour!-0) : \(min)"
                    }
                }
            }
        }
    }
}
struct ContinueChat : View {
    
    @State var chat : ChatModel
    
    @State var time = ""
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                if UserInfo.shared.MESSAGETICKET == 0 {
                    HStack{
                        Text(chat.chat_content)
                            .padding(5)
                            .font(.system(size: 15))
                            .foregroundColor(.black)
                            .background(Color(red: 230/255, green: 230/255, blue: 230/255))
                            .cornerRadius(10)
                            .blur(radius: 5)
                        
                        Text("확인하기")
                            .padding(10)
                            .font(.system(size: 15))
                            .foregroundColor(.white)
                            .background(LinearGradient(gradient: Gradient(colors: [Color(red: 255/255, green: 105/255, blue: 180/255), Color(red: 255/255, green: 176/255, blue: 244/255)]), startPoint: .leading, endPoint: .trailing))
                            .cornerRadius(15)
                    }
                }
                else {
                    Text(chat.chat_content)
                        .padding(5)
                        .font(.system(size: 15))
                        .foregroundColor(.black)
                        .background(Color(red: 230/255, green: 230/255, blue: 230/255))
                        .cornerRadius(10)
                }
            }
            VStack{
                Spacer()
                Text(time)
                    .font(.system(size: 10))
                    .foregroundColor(.gray)
            }
            Spacer()
        }
        .padding(.leading,43)
        .onAppear(){
            if self.chat.chat_time != nil {
                var timeSplit = self.chat.chat_time.split(separator: " ")[1].split(separator: ":")
                
                var hour = Int(timeSplit[0])
                var min = timeSplit[1]
                
                if hour! < 12 {
                    self.time = "오전 \(hour!-0) : \(min)"
                }
                else {
                    if hour != 12 {
                        self.time = "오후 \(hour!-12) : \(min)"
                    }
                    else {
                        self.time = "오후 \(hour!-0) : \(min)"
                    }
                }
            }
        }
    }
}

struct Menu : View {
    
    @Binding var showing : PresentationMode
    var room: ChatRoomModel
    
    var body: some View {
        
        VStack(spacing: 10) {
            HStack{
                Button(action: {
                    
                }){
                    Text("신고하기")
                        .font(.system(size: 13))
                }
                .foregroundColor(.black)
                Spacer()
            }
            .padding(10)
            HStack{
                Button(action: {
                    HttpService.shared.deleteReq(table: "chatroom", cond: "room_id='\(self.room.room_id)'"){
                        self.showing.dismiss()
                    }
                }){
                    Text("방나가기")
                        .font(.system(size: 13))
                }
                .foregroundColor(.red)
                Spacer()
            }
            .padding(.leading, 10)
            Spacer()
        }
        .frame(width: UIScreen.main.bounds.width*0.4)
        .background(Color.white)
        .edgesIgnoringSafeArea(.bottom)
    }
}
