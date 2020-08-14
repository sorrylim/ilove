//
//  ProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/21.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct ProfileView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var imageList : [UIImage] = []
    @State var index = 0
    
    @State var userId : String
    
    @State var newUser : NewUserModel?
    
    @State var userNickname : String
    @State var userCity : String
    @State var userAge : Int
    
    @State var userOption : UserOptionModel = UserOptionModel(user_id: "",user_purpose: nil,user_gender: "", user_height: nil, user_bodytype: nil, user_bloodtype: nil, user_residence: nil, user_job: nil, user_education: nil, user_holiday: nil, user_cigarette: nil, user_alcohol: nil, user_religion: nil, user_brother: nil, user_country: nil, user_salary: nil, user_asset: nil, user_marriagehistory: nil, user_children: nil,user_marriageplan: nil, user_childrenplan: nil, user_parenting: nil, user_wishdate: nil, user_datecost: nil, user_roommate: nil, user_language: nil, user_interest: nil, user_personality: nil, user_favoriteperson: nil, user_city: nil, user_introduce: nil, user_previewintroduce: nil)
    
    @State var isInit : Bool = false
    
    @State var interest : [String.SubSequence] = []
    @State var personality : [String.SubSequence] = []
    @State var favoritePerson : [String.SubSequence] = []
    
    
    @State var likeImage = Image(systemName: "heart")
    @State var meetImage = Image("call_icon")
    
    @State var expression = ExpressionModel(like: 0, meet: 0, show: 0)
    
    @State var likeAlertVisible = false
    @State var meetAlertVisible = false
    
    var body: some View {
        ZStack{
            ScrollView {
                if isInit {
                    VStack{
                        HStack{
                            Text(self.userNickname)
                                .fontWeight(.semibold)
                            Spacer()
                        }
                        .padding(.leading, 20)
                        HStack{
                            Text("\(userCity), \(userAge)")
                                .font(.system(size: 15))
                            Spacer()
                        }
                        .padding(.leading, 20)
                    }
                    .padding(.top, 10)
                    
                    ZStack{
                        PagingView(index: $index.animation(), maxIndex: imageList.count - 1) {
                            ForEach(self.imageList, id: \.self) { image in
                                Image(uiImage: image)
                                    .resizable()
                                    .cornerRadius(10)
                                    .scaledToFill()
                            }
                        }
                        .aspectRatio(4/4, contentMode: .fit)
                        .clipShape(RoundedRectangle(cornerRadius: 10))
                        .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.width)
                        .blur(radius: self.expression.show==0 ? 8 : 0)
                        
                        if self.expression.show==0 {
                            Button(action: {
                                let now = Date()
                                let dateFormatter = DateFormatter()
                                dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                let date=dateFormatter.string(from: now)
                                
                                HttpService.shared.insertShowExpressionReq(userId: UserInfo.shared.ID, partnerId: self.userId, date: date, candyCount: 0){
                                    self.expression.show=1
                                }
                            }){
                                Text("프로필 보기")
                                    .font(.system(size: 15))
                                    .foregroundColor(Color.white)
                                    .padding(10)
                                    .background(Color(red: 255/255, green: 160/255, blue: 0))
                                    .cornerRadius(15)
                            }
                            .shadow(color: Color.black.opacity(0.3),
                                    radius: 3,
                                    x: 3,
                                    y: 3)
                        }
                    }
                    VStack(alignment: .leading){
                        HStack{
                            Text("\(self.userOption.user_introduce ?? "등록안됨")")
                                .font(.system(size : 20))
                            Spacer()
                        }
                        .padding(.leading, 20)
                    }
                    .padding(.vertical, 10)
                    
                    VStack{
                        HStack{
                            Text("프로필")
                                .font(.system(size : 15))
                            Spacer()
                        }
                        .padding(.leading, 20)
                        .padding(.bottom, 20)
                        VStack(spacing: 10){
                            ProfileRow(key: "키", value: ((userOption.user_height != nil) ? userOption.user_height : "등록 안됨")!)
                            ProfileRow(key: "체형", value: ((userOption.user_bodytype != nil) ? userOption.user_bodytype : "등록 안됨")!)
                            ProfileRow(key: "혈액형", value: ((userOption.user_bloodtype != nil) ? userOption.user_bloodtype : "등록 안됨")!)
                            ProfileRow(key: "거주지", value: ((userOption.user_city != nil) ? userOption.user_city : "등록 안됨")!)
                            ProfileRow(key: "직업", value: ((userOption.user_job != nil) ? userOption.user_job : "등록 안됨")!)
                            ProfileRow(key: "학력", value: ((userOption.user_education != nil) ? userOption.user_education : "등록 안됨")!)
                            ProfileRow(key: "휴일", value: ((userOption.user_holiday != nil) ? userOption.user_holiday : "등록 안됨")!)
                            ProfileRow(key: "흡연", value: ((userOption.user_cigarette != nil) ? userOption.user_cigarette : "등록 안됨")!)
                            ProfileRow(key: "음주", value: ((userOption.user_alcohol != nil) ? userOption.user_cigarette : "등록 안됨")!)
                            ProfileRow(key: "종교", value: ((userOption.user_religion != nil) ? userOption.user_religion : "등록 안됨")!)
                            
                        }
                        VStack(spacing: 10){
                            ProfileRow(key: "형제", value: ((userOption.user_brother != nil) ? userOption.user_brother : "등록 안됨")!)
                            ProfileRow(key: "나라", value: ((userOption.user_country != nil) ? userOption.user_country : "등록 안됨")!)
                            ProfileRow(key: "수입", value: ((userOption.user_salary != nil) ? userOption.user_salary : "등록 안됨")!)
                            ProfileRow(key: "재산", value: ((userOption.user_asset != nil) ? userOption.user_asset : "등록 안됨")!)
                            ProfileRow(key: "결혼여부", value: ((userOption.user_marriagehistory != nil) ? userOption.user_marriagehistory : "등록 안됨")!)
                            ProfileRow(key: "자녀", value: ((userOption.user_children != nil) ? userOption.user_children : "등록 안됨")!)
                            ProfileRow(key: "결혼계획", value: ((userOption.user_marriageplan != nil) ? userOption.user_marriageplan : "등록 안됨")!)
                            ProfileRow(key: "자녀계획", value: ((userOption.user_childrenplan != nil) ? userOption.user_childrenplan : "등록 안됨")!)
                            ProfileRow(key: "육아", value: ((userOption.user_parenting != nil) ? userOption.user_parenting : "등록 안됨")!)
                            ProfileRow(key: "원하는 데이트", value: ((userOption.user_wishdate != nil) ? userOption.user_wishdate : "등록 안됨")!)
                            
                        }
                        VStack(spacing: 10){
                            ProfileRow(key: "데이트 비용", value: ((userOption.user_datecost != nil) ? userOption.user_datecost : "등록 안됨")!)
                            ProfileRow(key: "룸메이트", value: ((userOption.user_roommate != nil) ? userOption.user_roommate : "등록 안됨")!)
                            ProfileRow(key: "언어", value: ((userOption.user_language != nil) ? userOption.user_language : "등록 안됨")!)
                        }
                    }
                    .padding(.vertical, 10)
                    .padding(.horizontal, 20)
                    if interest.count > 0 {
                        VStack{
                            HStack{
                                Text("나의 취미는")
                                    .font(.system(size : 15))
                                Spacer()
                            }
                            .padding(.leading, 20)
                            DynamicHeightHStack(items: interest)
                                .padding(.leading, 20)
                        }
                        .padding(.bottom,20)
                        .padding(.top,10)
                    }
                    
                    if personality.count > 0 {
                        VStack{
                            HStack{
                                Text("나의 성격은")
                                    .font(.system(size : 15))
                                Spacer()
                            }
                            .padding(.leading, 20)
                            DynamicHeightHStack(items: personality)
                                .padding(.leading, 20)
                        }
                        .padding(.bottom,20)
                    }
                    
                    if favoritePerson.count > 0 {
                        VStack{
                            HStack{
                                Text("내가 좋아하는 친구는")
                                    .font(.system(size : 15))
                                Spacer()
                            }
                            .padding(.leading, 20)
                            DynamicHeightHStack(items: favoritePerson)
                                .padding(.leading, 20)
                        }
                        .padding(.bottom,110)
                    }
                }
            }
            VStack{
                Spacer()
                HStack{
                    Button(action: {
                        if self.expression.meet==1 {
                            HttpService.shared.deleteExpressionReq(userId: UserInfo.shared.ID, partnerId: self.userId, expressionType: "meet") { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.expression.meet=0
                                }
                            }
                        }
                        else {
                            let now = Date()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                            let date=dateFormatter.string(from: now)
                            
                            HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.userId, expressionType: "meet", expressionDate: date) { (resultModel) -> Void in
                                self.expression.meet=1
                                if resultModel.result=="success" {
                                    self.meetImage=Image("call_icon")
                                }
                                else if resultModel.result=="eachsuccess" {
                                    self.meetImage=Image("call_icon")
                                    self.meetAlertVisible=true
                                }
                            }
                        }
                    }){
                        meetImage
                            .resizable()
                            .frame(width: 15, height: 15)
                            .foregroundColor(self.expression.meet==1 ? Color(red:255/255, green: 160/255, blue: 0/255) : .gray)
                    }
                    .frame(width: 50, height: 50)
                    .background(Color.white)
                    .clipShape(Circle())
                    .padding(20)
                    .shadow(color: Color.black.opacity(0.3),
                            radius: 3,
                            x: 3,
                            y: 3)
                    
                    Spacer()
                    Button(action: {
                        if self.expression.like==1 {
                            HttpService.shared.deleteExpressionReq(userId: UserInfo.shared.ID, partnerId: self.userId, expressionType: "like") { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.expression.like=0
                                }
                            }
                        }
                        else {
                            let now = Date()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                            let date=dateFormatter.string(from: now)
                            
                            HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.userId, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.expression.like=1
                                }
                                else if resultModel.result=="eachsuccess" {
                                    self.expression.like=1
                                    self.likeAlertVisible=true
                                }
                            }
                        }
                    }){
                        likeImage
                            .foregroundColor(self.expression.like==1 ? Color(red:255/255, green: 160/255, blue: 0/255) : .gray)
                    }
                    .frame(width: 50, height: 50)
                    .background(Color.white)
                    .clipShape(Circle())
                    .padding(20)
                    .shadow(color: Color.black.opacity(0.3),
                            radius: 3,
                            x: 3,
                            y: 3)
                }
            }
            if likeAlertVisible {
                GeometryReader{_ in
                    EachLikeAlert(userId: self.userId, userNickname: self.userNickname, userAge: self.userAge, userCity: self.userCity, uiImage: self.imageList[0], showing: self.$likeAlertVisible)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
            if meetAlertVisible {
                GeometryReader{_ in
                    EachMeetAlert(userId: self.userId, userNickname: self.userNickname, userAge: self.userAge, userCity: self.userCity, uiImage: self.imageList[0], showing: self.$meetAlertVisible)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
        .onAppear(){
            HttpService.shared.getPartnerExpressionReq(userId: UserInfo.shared.ID, partnerId: self.userId){ expressionModel in
                self.expression=expressionModel
                
                if self.expression.like == 1 {
                    self.likeImage = Image(systemName: "heart.fill")
                }
                else {
                    self.likeImage = Image("heart")
                }
                
                if self.expression.meet == 1 {
                    self.meetImage = Image("call_icon")
                }
                else {
                    self.meetImage = Image("call_n_icon")
                }
            }
            
            HttpService.shared.getProfileImageReq(userId: self.userId) { profileImageModelArray in
                for i in 0..<profileImageModelArray.count {
                    KingfisherManager.shared.retrieveImage(with: URL(string: profileImageModelArray[i].image!)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                        self.imageList.append(image!)
                    })
                }
                while self.imageList.count != profileImageModelArray.count {}
                HttpService.shared.getUserOptionReq(userId: self.userId) { userOptionModel in
                    self.userOption=userOptionModel
                    
                    if self.userOption.user_interest != nil {
                        self.interest = self.userOption.user_interest!.split(separator: ",")
                    }
                    
                    if self.userOption.user_personality != nil {
                        self.personality = self.userOption.user_personality!.split(separator: ",")
                    }
                    
                    if self.userOption.user_favoriteperson != nil {
                        self.favoritePerson = self.userOption.user_favoriteperson!.split(separator: ",")
                    }
                    self.isInit=true
                }
            }
            
        }
        
    }
}

struct ProfileRow : View{
    
    @State var key : String
    @State var value : String
    
    var body: some View{
        VStack{
            HStack{
                HStack{
                    Text(self.key)
                        .font(.system(size:13))
                        .foregroundColor(Color.gray)
                    Spacer()
                    Text(self.value)
                        .font(.system(size:13))
                        .foregroundColor(Color(red: 255/255, green: 160/255, blue: 0/255))
                }
            }
        }
    }
}
