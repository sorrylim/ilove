//
//  ListView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import CoreLocation
import class Kingfisher.KingfisherManager

struct UserListView : View{
    
    @State var userList:[UserModel] = []
    @State var upUserList:[UserModel] = []
    
    @State var alertUserId = ""
    @State var alertUserNickname = ""
    @State var alertUserAge = 0
    @State var alertUserCity = ""
    @State var alertUiImage = UIImage()
    @State var alertVisible = false
    
    @State var textSortDist = Text("거리순")
    @State var textSortTime = Text("접속시간순")
    
    var body : some View{
        ZStack{
            VStack{
                HStack{
                    Spacer()
                    textSortDist
                        .font(.system(size: 13))
                        .foregroundColor(Color.black)
                        .onTapGesture {
                            self.sortByDist()
                    }
                    Text("|")
                    textSortTime
                        .font(.system(size: 13))
                        .foregroundColor(Color.gray)
                        .onTapGesture {
                            self.sortByTime()
                    }
                }
                .padding(.top,10)
                
                if self.upUserList.count > 0 {
                    List{
                        ForEach(upUserList, id: \.user_id){user in
                            UserRow(user: user, view: self)
                        }
                    }
                    .frame(height: CGFloat(self.upUserList.count * 80))
                    .moveDisabled(true)
                    .onAppear(){
                        UITableView.appearance().separatorStyle = .none
                    }
                }
                List{
                    ForEach(userList, id: \.user_id){user in
                        UserRow(user: user, view: self)
                    }
                }
                .onAppear(){
                    UITableView.appearance().separatorStyle = .none
                }
                .navigationBarTitle("리스트",displayMode: .inline)
                .onAppear(){
                    ContentView.rootView?.setTitle(title: "리스트")
                    
                    if UserInfo.shared.GENDER == "M"{
                        HttpService.shared.getUpProfileUserListReq(gender: "F", userId: UserInfo.shared.ID) { (userModelArray) -> Void in
                            self.upUserList=userModelArray
                        }
                        HttpService.shared.getUserListReq(gender: "F", userId: UserInfo.shared.ID) { (userModelArray) -> Void in
                            self.userList=userModelArray
                            self.sortByDist()
                        }
                    }
                    else {
                        HttpService.shared.getUpProfileUserListReq(gender: "M", userId: UserInfo.shared.ID) { (userModelArray) -> Void in
                            self.upUserList=userModelArray
                            print(self.upUserList)
                        }
                        
                        HttpService.shared.getUserListReq(gender: "M", userId: UserInfo.shared.ID) { (userModelArray) -> Void in
                            self.userList=userModelArray
                            self.sortByDist()
                        }
                    }
                }
                
            }
            if alertVisible {
                GeometryReader{_ in
                    EachLikeAlert(userId: self.alertUserId, userNickname: self.alertUserNickname, userAge: self.alertUserAge, userCity: self.alertUserCity,uiImage: self.alertUiImage, showing: self.$alertVisible)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
    }
    
    mutating func setVisible(userId: String, userNickname: String, userAge: Int, userCity: String,uiImage:UIImage, alertVisible: Bool){
        self.alertUserId=userId
        self.alertUserNickname=userNickname
        self.alertUserAge=userAge
        self.alertUserCity=userCity
        self.alertUiImage=uiImage
        self.alertVisible=alertVisible
    }
    
    func sortByDist(){
        self.textSortDist = Text("거리순")
            .font(.system(size: 13))
            .foregroundColor(Color.black)
        self.textSortTime = Text("접속시간순")
            .font(.system(size: 13))
            .foregroundColor(Color.gray)
        
        self.userList = self.userList.sorted(by:{
            $0.user_recentgps > $1.user_recentgps
        })
    }
    
    func sortByTime(){
        self.textSortDist = Text("거리순")
            .font(.system(size: 13))
            .foregroundColor(Color.gray)
        self.textSortTime = Text("접속시간순")
            .font(.system(size: 13))
            .foregroundColor(Color.black)
        
        self.userList = self.userList.sorted(by:{
            $0.user_recenttime > $1.user_recenttime
        })
    }
}

struct UserRow : View{
    
    @ObservedObject private var locationManager = LocationManager()
    
    @State var profileVisible = false
    
    @State var user : UserModel
    @State var view : UserListView
    
    @State var uiImage = UIImage()
    
    @State var meetImage = Image("call_icon")
    @State var likeImage: Image = Image(systemName: "heart")
    
    @State var age = 0
    
    @State var dist = ""
    @State var time = ""
    
    var body : some View{
        
        HStack(spacing: 10){
            Image(uiImage: self.uiImage)
                .resizable()
                .frame(width: 70, height: 70)
                .cornerRadius(10)
                .onTapGesture {
                    self.profileVisible=true
            }
            .sheet(isPresented: $profileVisible){
                ProfileView(userId:self.user.user_id,userNickname:self.user.user_nickname,userCity:self.user.user_city,userAge:self.age)
            }
            
            VStack(alignment: .leading, spacing: 10){
                HStack{
                    Text(user.user_purpose)
                        .font(.system(size : 13))
                        .foregroundColor(.white)
                        .frame(width:30, height:20)
                        .background(Color(red: 255/255, green: 160/255, blue:0/255))
                        .cornerRadius(10)
                    Text("\(user.user_nickname) \(age) \(user.user_city)")
                        .font(.system(size : 13))
                        .foregroundColor(.gray)
                }
                if user.user_previewintroduce != nil {
                    Text(user.user_previewintroduce!)
                        .font(.system(size: 12))
                }
                else {
                    Text("")
                        .font(.system(size:12))
                }
                Spacer()
            }
            Spacer()
            VStack(alignment: .trailing){
                Text("\(dist), \(time)")
                    .font(.system(size: 10))
                    .foregroundColor(Color.gray)
                Spacer()
                HStack(spacing: 10) {
                    meetImage
                        .resizable()
                        .frame(width: 15, height: 15)
                        .onAppear(){
                            if self.user.meet==1 {
                                self.meetImage=Image("call_icon")
                            }
                            else {
                                self.meetImage=Image("call_n_icon")
                            }
                    }
                    .onTapGesture {
                        if self.user.meet==1 {
                            print(self.user)
                            HttpService.shared.deleteExpressionReq(userId: UserInfo.shared.ID, partnerId: self.user.user_id, expressionType: "meet") { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.meetImage=Image("call_n_icon")
                                }
                                self.user.meet=0
                            }
                        }
                        else {
                            let now = Date()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                            let date=dateFormatter.string(from: now)
                            
                            HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.user.user_id, expressionType: "meet", expressionDate: date) { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.meetImage=Image("call_icon")
                                }
                                else if resultModel.result=="eachsuccess" {
                                    self.meetImage=Image("call_icon")
                                }
                                self.user.meet=1
                            }
                        }
                    }
                    likeImage
                        .foregroundColor(self.user.like==1 ? Color(red: 255/255, green: 160/255, blue: 0/255) : .gray)
                        .frame(width: 15, height: 15)
                        .onAppear(){
                            if self.user.like==1 {
                                self.likeImage=Image(systemName: "heart.fill")
                            }
                            else {
                                self.likeImage=Image(systemName: "heart")
                            }
                    }
                    .onTapGesture {
                        if self.user.like==1 {
                            print(self.user)
                            HttpService.shared.deleteExpressionReq(userId: UserInfo.shared.ID, partnerId: self.user.user_id, expressionType: "like") { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.likeImage=Image(systemName: "heart")
                                }
                                self.user.like=0
                            }
                        }
                        else {
                            let now = Date()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                            let date=dateFormatter.string(from: now)
                            
                            HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.user.user_id, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.likeImage=Image(systemName: "heart.fill")
                                }
                                else if resultModel.result=="eachsuccess" {
                                    self.likeImage=Image(systemName: "heart.fill")
                                    self.view.setVisible(userId: self.user.user_id, userNickname: self.user.user_nickname, userAge: self.age, userCity: self.user.user_city,uiImage: self.uiImage, alertVisible: true)
                                }
                                self.user.like=1
                            }
                        }
                    }
                }
            }
        }
        .frame(height:80)
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.user.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
            
            let coordinate = self.locationManager.location != nil
                ? self.locationManager.location!.coordinate
                : CLLocationCoordinate2D()
            
            var userLocation=CLLocation(latitude: (self.user.user_recentgps.split(separator: ",")[0] as NSString).doubleValue
                , longitude: (self.user.user_recentgps.split(separator: ",")[1] as NSString).doubleValue)
            
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
            let date=dateFormatter.string(from: now)
            
            var birthday=self.user.user_birthday
            var start=birthday.index(birthday.startIndex,offsetBy: 0)
            var end=birthday.index(birthday.startIndex, offsetBy: 3)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(birthday[start...end]).unsafelyUnwrapped+1)
            
            var userRecentTime = dateFormatter.date(from : self.user.user_recenttime)?.addingTimeInterval(3600 * 9)
            now=now.addingTimeInterval(3600 * 9)
            
            var timeInterval = Int(now.timeIntervalSince(userRecentTime!))
            
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
