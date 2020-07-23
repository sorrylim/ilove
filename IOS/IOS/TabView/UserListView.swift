//
//  ListView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct UserListView : View{
    
    @State var userList:[UserModel] = []
    
    @State var alertUserId = ""
    @State var alertUserNickname = ""
    @State var alertUserAge = 0
    @State var alertUserRecentTime = ""
    @State var alertUiImage = UIImage()
    @State var alertVisible = false
    
    var body : some View{
        //NavigationView{
            ZStack{
                List{
                    ForEach(userList, id: \.user_id){user in
                        UserRow(user: user, view: self)
                        
                    }
                }
                .navigationBarTitle("리스트",displayMode: .inline)
                .onAppear(){
                    HttpService.shared.getUserListReq(gender: UserInfo.shared.GENDER, userId: UserInfo.shared.ID) { (userModelArray) -> Void in
                        self.userList=userModelArray
                    }
                }
                if alertVisible {
                    GeometryReader{_ in
                        EachAlert(userId: self.alertUserId, userNickname: self.alertUserNickname, userAge: self.alertUserAge, userRecentTime: self.alertUserRecentTime,uiImage: self.alertUiImage, showing: self.$alertVisible)
                    }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
                }
            //}
        }
    }
    
    mutating func setVisible(userId: String, userNickname: String, userAge: Int, userRecentTime: String,uiImage:UIImage, alertVisible: Bool){
        self.alertUserId=userId
        self.alertUserNickname=userNickname
        self.alertUserAge=userAge
        self.alertUserRecentTime=userRecentTime
        self.alertUiImage=uiImage
        self.alertVisible=alertVisible
    }
}

struct UserRow : View{
    
    @State var user : UserModel
    @State var view : UserListView
    
    
    @State var uiImage = UIImage()
    
    @State var meetImage = Image("call_icon")
    @State var likeImage: Image = Image(systemName: "heart")
    
    @State var age = 0
    
    var body : some View{
        
        HStack(spacing: 20){
            
            Image(uiImage: self.uiImage)
                .resizable()
                .frame(width: 70, height: 70)
                .cornerRadius(10)
            
            VStack(alignment: .leading, spacing: 10){
                Text("\(user.user_nickname), \(age), \(user.user_recenttime), \(user.user_recentgps)")
                    .font(.system(size : 10))
                    .foregroundColor(.gray)
                if user.user_previewintroduce != nil {
                    Text(user.user_previewintroduce!)
                        .font(.system(size: 15))
                }
                else {
                    Text("")
                        .font(.system(size:15))
                }
                Spacer()
            }
            Spacer()
            VStack{
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
                        .foregroundColor(.red)
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
                                    self.view.setVisible(userId: self.user.user_id, userNickname: self.user.user_nickname, userAge: self.age, userRecentTime: self.user.user_recenttime,uiImage: self.uiImage, alertVisible: true)
                                }
                                self.user.like=1
                            }
                        }
                    }
                }
            }
        }
        .padding(10)
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.user.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
            
            let now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(self.user.user_birthday.split(separator: "-")[0]).unsafelyUnwrapped+1)
        }
    }
}
