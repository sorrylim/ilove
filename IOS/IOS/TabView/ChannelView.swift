//
//  ChannelView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import CoreLocation

import class Kingfisher.KingfisherManager

struct ChannelView : View{
    
    @State var showModal = false
    
    @State var newUserList:[NewUserModel]=[]
    
    @State var profileCount : Int = -1
    @State var storyCount : Int = -1
    
    @State var sendLikeCount : Int = -1
    @State var receiveLikeCount : Int = -1
    @State var eachLikeCount : Int = -1
    
    @State var sendMeetCount : Int = -1
    @State var receiveMeetCount : Int = -1
    @State var eachMeetCount : Int = -1
    
    @State var alertUpProfileVisible = false
    var body: some View {
        ScrollView(){
            //아이러브 신규 이성
            Group{
                HStack{
                    Text("새로 가입한 친구!")
                        .font(.system(size: 18))
                    Spacer()
                }
                
                VStack(spacing: 15){
                    if newUserList.count > 0 {
                        HStack{
                            NewUserCell(newUser: newUserList[0])
                            if newUserList.count > 1 {
                                NewUserCell(newUser: newUserList[1])
                            }
                            else {
                                
                                Text("")
                                    .frame(width:160,height:160)
                            }
                        }
                    }
                    if newUserList.count > 2 {
                        HStack{
                            NewUserCell(newUser: newUserList[2])
                            if newUserList.count > 3 {
                                NewUserCell(newUser: newUserList[3])
                            }
                            else {
                                Text("")
                                    .frame(width:160,height:160)
                            }
                        }
                    }
                }
                
                Spacer()
            }
            
            //내 프로필 확인한 사람 & 내 스토리를 확인한 사람
            Group{
                ZStack{
                    VStack(spacing: 10){
                        Spacer()
                        NavigationLink(destination: PartnerView(title : "내 프로필을 확인한 사람")){
                            HStack{
                                Text("내 프로필을 확인한 사람")
                                    .font(.system(size: 15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(profileCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(profileCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                        
                        NavigationLink(destination: PartnerView(title : "내 스토리를 확인한 사람")){
                            HStack{
                                Text("내 스토리를 확인한 사람")
                                    .font(.system(size: 15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(storyCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(storyCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                        
                        NavigationLink(destination: PartnerView(title: "내가 좋아요를 보낸 이성")){
                            HStack{
                                Text("내가 좋아요를 보낸 이성")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(sendLikeCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(sendLikeCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                        
                        NavigationLink(destination: PartnerView(title: "나에게 좋아요를 보낸 이성")){
                            HStack{
                                Text("나에게 좋아요를 보낸 이성")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(receiveLikeCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(receiveLikeCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                        
                        NavigationLink(destination: PartnerView(title: "서로 좋아요가 연결된 이성")){
                            HStack{
                                Text("서로 좋아요가 연결된 이성")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(eachLikeCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(eachLikeCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                    }
                    .frame(width:UIScreen.main.bounds.width*0.85)
                    .padding(15)
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(red: 255/255, green: 160/255, blue: 0),lineWidth: 2))
                    .padding(.top,10)
                    VStack{
                        Text("  심쿵한 그대  ")
                            .font(.system(size: 18))
                            .background(Color.white)
                        Spacer()
                    }
                    
                }
            }
            .padding(.bottom,10)
            
            Group{
                ZStack{
                    VStack(spacing: 10){
                        Spacer()
                        NavigationLink(destination: PartnerView(title : "내가 만나고 싶은 그대")){
                            HStack{
                                Text("내가 만나고 싶은 그대")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(sendMeetCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(sendMeetCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                        
                        
                        NavigationLink(destination: PartnerView(title : "나를 만나고 싶어하는 그대")){
                            HStack{
                                Text("나를 만나고 싶어하는 그대")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(receiveMeetCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(receiveMeetCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                        
                        NavigationLink(destination: PartnerView(title : "이제 만날수 있는 그대")){
                            HStack{
                                Text("이제 만날수 있는 그대")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(eachMeetCount)명")
                                    .foregroundColor(Color.black)
                            }
                            .padding(5)
                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(eachMeetCount > 0 ? Color(red: 255/255, green: 160/255, blue: 0) : Color.white,lineWidth: 1))
                        }
                    }
                    .frame(width:UIScreen.main.bounds.width*0.85)
                    .padding(15)
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(red: 255/255, green: 160/255, blue: 0),lineWidth: 2))
                    .padding(.top,10)
                    VStack{
                        Text("  심쿵 미팅  ")
                            .font(.system(size: 18))
                            .background(Color.white)
                        Spacer()
                    }
                }
                
            }
            .padding(.bottom,10)
            
            Group{
                HStack{
                    Image("icon")
                        .resizable()
                        .frame(width:60,height:60)
                    VStack(alignment: .leading){
                        Text("내 프로필 상단으로 올리기")
                            .font(.system(size:18))
                        Text("1시간 동안 유지됩니다.")
                            .font(.system(size:12))
                            .foregroundColor(Color.gray)
                    }
                    .onTapGesture {
                        self.alertUpProfileVisible=true
                    }
                    Spacer()
                }
                .padding(.leading,20)
                .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(red: 255/255, green: 160/255, blue: 0),lineWidth: 2))
                Spacer(minLength: 30)
            }
            
        }
        .alert(isPresented: self.$alertUpProfileVisible){
            Alert(title: Text("프로필 올리기").font(.system(size: 15))
                , message: Text("\n프로필 올리기를 선택하셨습니다\n(리스트에 1시간 노출)").font(.system(size:12)).foregroundColor(.gray)
                , primaryButton: .destructive(Text("취소"),action: {
                    
                })
                ,secondaryButton: .cancel(Text("확인"), action: {
                    let now = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                    let date=dateFormatter.string(from: now)
                    
                    HttpService.shared.updateUpProfileReq(userId: UserInfo.shared.ID, date: date)
                })
            )
        }
        .padding()
        .navigationBarTitle("채널",displayMode: .inline)
        .onAppear{
            ContentView.rootView?.setTitle(title: "채널")
            
            if UserInfo.shared.GENDER == "M" {
                HttpService.shared.getNewUserReq(userGender: "F", callback: { (newUserModelArray) -> Void in
                    self.newUserList=newUserModelArray
                })
            }
            else {
                HttpService.shared.getNewUserReq(userGender: "M", callback: { (newUserModelArray) -> Void in
                    self.newUserList=newUserModelArray
                })
            }
            
            
            HttpService.shared.getViewCountReq(userId: UserInfo.shared.ID,callback: { (data) -> Void in
                self.profileCount=data.profile
                self.storyCount=data.story
            })
            
            HttpService.shared.getExpressionCountReq(userId: UserInfo.shared.ID, callback: { (data) -> Void in
                self.sendLikeCount=data.send_like
                self.receiveLikeCount=data.receive_like
                self.eachLikeCount=data.each_like
                
                self.sendMeetCount=data.send_meet
                self.receiveMeetCount=data.receive_meet
                self.eachMeetCount=data.each_meet
            })
        }
    }
}

struct ChannelView_Previews: PreviewProvider {
    static var previews: some View {
        ChannelView()
    }
}

struct NewUserCell: View {
    
    @ObservedObject private var locationManager = LocationManager()
    
    @State var profileVisible = false
    
    @State var newUser : NewUserModel
    
    @State var uiImage = UIImage()
    @State var age = 0
    @State var dist = ""
    @State var time = ""
    var body: some View {
        ZStack{
            Image(uiImage: self.uiImage)
                .resizable()
                .frame(width: 160,height: 160)
                .cornerRadius(10)
                
            
            VStack(spacing:10){
                Spacer()
                HStack{
                    Text("\(newUser.user_nickname),\(self.age)")
                        .foregroundColor(Color.white)
                        .font(.system(size: 15, weight: .bold))
                    Spacer()
                }
                HStack{
                    Text("\(dist),\(time)")
                        .foregroundColor(Color.white)
                        .font(.system(size: 10, weight: .bold))
                    Spacer()
                }
            }
            .padding()
            .onAppear(){
                KingfisherManager.shared.retrieveImage(with: URL(string: self.newUser.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                    self.uiImage=image!
                })
                
                let coordinate = self.locationManager.location != nil
                    ? self.locationManager.location!.coordinate
                    : CLLocationCoordinate2D()
                
                var userLocation=CLLocation(latitude: (self.newUser.user_recentgps.split(separator: ",")[0] as NSString).doubleValue
                    , longitude: (self.newUser.user_recentgps.split(separator: ",")[1] as NSString).doubleValue)
                
                var mylocation=CLLocation(latitude: coordinate.latitude, longitude: coordinate.longitude)
                
                var distMeter = mylocation.distance(from: userLocation)
                var distKm=distMeter/1000
                
                if distKm>=10 {
                    self.dist="\(Int(distKm))km"
                }
                else {
                    self.dist=String(format: "%.1f", distKm)+"km"
                }
                
                var now = Date()
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                dateFormatter.locale = Locale(identifier: "ko")
                let date=dateFormatter.string(from: now)
                
                var birthday=self.newUser.user_birthday
                var start=birthday.index(birthday.startIndex,offsetBy: 0)
                var end=birthday.index(birthday.startIndex, offsetBy: 3)
                
                self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(birthday[start...end]).unsafelyUnwrapped+1)
                
                var newUserRecentTime = dateFormatter.date(from : self.newUser.user_recenttime)?.addingTimeInterval(3600 * 9)
                now=now.addingTimeInterval(3600 * 9)
                
                var timeInterval = Int(now.timeIntervalSince(newUserRecentTime!))
                
                if timeInterval >= 86400 {
                    self.time="\(timeInterval/86400)일 전"
                }
                else if timeInterval >= 3600 {
                    self.time="\(timeInterval/3600)시간 전"
                }
                else if timeInterval >= 60{
                    self.time="\(timeInterval/60)분 전"
                }
                else {
                    self.time="\(timeInterval)초 전"
                }
            }
        }
        .sheet(isPresented: $profileVisible){
            ProfileView(userId:self.newUser.user_id,userNickname:self.newUser.user_nickname,userCity:self.newUser.user_city,userAge:self.age)
        }
        .onTapGesture {
            self.profileVisible=true
        }
    }
}

