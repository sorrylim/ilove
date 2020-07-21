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
    
    var body: some View {
        ScrollView(){
            //아이러브 신규 이성
            Group{
                HStack{
                    Text("아이러브 신규 이성")
                        .font(.system(size: 20))
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
                
                Spacer(minLength: 30)
            }
            
            //내 프로필 확인한 사람 & 내 스토리를 확인한 사람
            Group{
                HStack{
                    Text("향기를 남기고 간 그대는")
                        .font(.system(size: 20))
                    Spacer()
                }
                Spacer(minLength: 10)
                VStack(spacing: 20){
                    NavigationLink(destination: PartnerView(title : "내 프로필을 확인한 사람")){
                        HStack{
                            Text("내 프로필을 확인한 사람")
                                .font(.system(size: 15))
                                .foregroundColor(Color.gray)
                            Spacer()
                            Text("\(profileCount)명")
                                .foregroundColor(Color.black)
                        }
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
                    }
                }
                .padding()
                .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.orange,lineWidth: 2))
                Spacer(minLength: 30)
            }
            
            Group{
                HStack{
                    Text("아이러브 좋아요")
                        .font(.system(size: 20))
                    Spacer()
                }
                VStack(spacing: 20){
                    NavigationLink(destination: PartnerView(title: "내가 좋아요를 보낸 이성")){
                        HStack{
                            Text("내가 좋아요를 보낸 이성")
                                .font(.system(size:15))
                                .foregroundColor(Color.gray)
                            Spacer()
                            Text("\(sendLikeCount)명")
                                .foregroundColor(Color.black)
                        }
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
                    }
                }
                .padding()
                Spacer(minLength: 30)
            }
            
            Group{
                HStack{
                    Text("우리 만나봐요!")
                        .font(.system(size: 20))
                    Spacer()
                }
                VStack(spacing: 20){
                    
                    NavigationLink(destination: PartnerView(title : "내가 만나고 싶은 그대")){
                        HStack{
                            Text("내가 만나고 싶은 그대")
                                .font(.system(size:15))
                                .foregroundColor(Color.gray)
                            Spacer()
                            Text("\(sendMeetCount)명")
                                .foregroundColor(Color.black)
                        }
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
                    }
                    
                    NavigationLink(destination: PartnerView(title : "서로 연락처를 주고받은 그대")){
                        HStack{
                            Text("서로 연락처를 주고받은 그대")
                                .font(.system(size:15))
                                .foregroundColor(Color.gray)
                            Spacer()
                            Text("\(eachMeetCount)명")
                                .foregroundColor(Color.black)
                        }
                    }
                }
                .padding()
                Spacer(minLength: 30)
            }
            
            Group{
                HStack{
                    Image("icon")
                        .resizable()
                        .frame(width:60,height:60)
                    VStack(alignment: .leading){
                        Text("내 프로필 상단으로 올리기")
                            .font(.system(size:20))
                        Text("많은 이성들에게 내 프로필을 보여주세요")
                            .font(.system(size:15))
                            .foregroundColor(Color.gray)
                    }
                    Spacer()
                }
                Spacer(minLength: 30)
            }
            
        }
        .padding()
        .navigationBarTitle("채널",displayMode: .inline)
        .onAppear{
            ContentView.rootView?.setTitle(title: "채널")
            
            HttpService.shared.getNewUserReq(userGender: "F", callback: { (newUserModelArray) -> Void in
                self.newUserList=newUserModelArray
            })
            
            
            HttpService.shared.getViewCountReq(userId: "ksh",callback: { (data) -> Void in
                self.profileCount=data.profile
                self.storyCount=data.story
            })
            
            HttpService.shared.getExpressionCountReq(userId: "ksh", callback: { (data) -> Void in
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
    
    @State var newUser : NewUserModel
    
    @State var uiImage = UIImage()
    //@State var storyVisible=false
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
                
                self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(self.newUser.user_birthday.split(separator: "-")[0]).unsafelyUnwrapped+1)
                
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
        
    }
}

