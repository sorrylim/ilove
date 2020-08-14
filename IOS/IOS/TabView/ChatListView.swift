//
//  ChatRoomView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager


struct ChatListView : View{
    
    @State var roomList : [ChatRoomModel] = []
    
    var body: some View{
        VStack{
            List{
                ForEach(roomList, id:\.room_id){ room in
                    RoomRow(room: room)
                }
            }
            .onAppear(){
                UITableView.appearance().separatorStyle = .none
            }
        }
        .onAppear(){
            ContentView.rootView?.setTitle(title: "대화방")
                    
            HttpService.shared.getMyRoomReq(userId: UserInfo.shared.ID){ (chatRoomModelArray) -> Void in
                self.roomList=chatRoomModelArray
            }
        }
    }
}

struct RoomRow : View {
    
    @State var room = ChatRoomModel(room_id: "", room_maker: "", room_partner: "", room_title: "", chat_content: "", chat_time: "")
    
    @State var roomImage = UIImage()
    
    @State var title=""
    @State var content=""
    @State var time=""
    
    var body: some View{
        NavigationLink(destination: ChatView(room: room, roomImage: roomImage, isNew: false)){
            HStack(spacing: 10){
                Image(uiImage: roomImage)
                    .resizable()
                    .frame(width: 50,height: 50)
                    .cornerRadius(15)
                VStack(alignment: .leading){
                    Text(title)
                        .padding(.top,15)
                    Text(content)
                        .font(.system(size: 13))
                        .foregroundColor(Color.gray)
                        .frame(height: 40)
                    Spacer()
                }
                .padding(.leading,10)
                Spacer()
                VStack{
                    Text(time)
                        .font(.system(size: 13))
                        .foregroundColor(Color.gray)
                        .padding(.top,15)
                    Spacer()
                }
                .padding(.trailing,10)
            }
        }
        .onAppear(){
            HttpService.shared.getProfileImageReq(userId: self.room.room_partner){ (profileImageModelArray) -> Void in
                KingfisherManager.shared.retrieveImage(with: URL(string: profileImageModelArray[0].image!)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                    self.roomImage=image!
                })
            }
            
            if UserInfo.shared.NICKNAME == String(self.room.room_title!.split(separator: "&")[0]) {
                self.title=String(self.room.room_title!.split(separator: "&")[1])
            }
            else {
                self.title=String(self.room.room_title!.split(separator: "&")[0])
            }
            
            if self.room.chat_content != nil {
                self.content = String((self.room.chat_content!.split(separator: "|")[0]))
            }
            else {
                self.content = ""
            }
            
            if self.room.chat_time != nil {
                var timeSplit = self.room.chat_time!.split(separator: " ")[1].split(separator: ":")
                
                var hour = Int(timeSplit[0])
                var min = timeSplit[1]
                
                if hour! < 12 {
                    self.time = "오전 \(hour) : \(min)"
                }
                else {
                    if hour != 12 {
                        self.time = "오후 \(hour!-12) : \(min)"
                    }
                    else {
                        self.time = "오후 \(hour) : \(min)"
                    }
                }
            }
        }
    }
}
