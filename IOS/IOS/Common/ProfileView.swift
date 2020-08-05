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
    
    @State var userNickname : String
    @State var userCity : String
    @State var userAge : Int
    
    @State var userOption : UserOptionModel = UserOptionModel(user_id: "", user_height: nil, user_bodytype: nil, user_bloodtype: nil, user_residence: nil, user_job: nil, user_education: nil, user_holiday: nil, user_cigarette: nil, user_alcohol: nil, user_religion: nil, user_brother: nil, user_country: nil, user_salary: nil, user_asset: nil, user_marriagehistory: nil, user_children: nil,user_marriageplan: nil, user_childrenplan: nil, user_parenting: nil, user_wishdate: nil, user_datecost: nil, user_roommate: nil, user_language: nil, user_interest: nil, user_personality: nil, user_favoriteperson: nil, user_city: nil, user_introduce: nil, user_previewintroduce: nil, user_gender: nil)
    
    @State var isInit : Bool = false
    
    @State var interest : [String.SubSequence] = []
    @State var personality : [String.SubSequence] = []
    @State var favoritePerson : [String.SubSequence] = []
    
    var body: some View {
        
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
                        if userOption.user_height != nil {
                            ProfileRow(key: "키", value: userOption.user_height!)
                        }
                        if userOption.user_bodytype != nil {
                            ProfileRow(key: "체형", value: userOption.user_bodytype!)
                        }
                        if userOption.user_bloodtype != nil {
                            ProfileRow(key: "혈액형", value: userOption.user_bloodtype!)
                        }
                        if userOption.user_job != nil {
                            ProfileRow(key: "직업", value: userOption.user_job!)
                        }
                        if userOption.user_education != nil {
                            ProfileRow(key: "학력", value: userOption.user_education!)
                        }
                        if userOption.user_holiday != nil {
                            ProfileRow(key: "휴일", value: userOption.user_holiday!)
                        }
                        if userOption.user_cigarette != nil {
                            ProfileRow(key: "담배", value: userOption.user_cigarette!)
                        }
                        if userOption.user_alcohol != nil {
                            ProfileRow(key: "음주", value: userOption.user_alcohol!)
                        }
                        if userOption.user_religion != nil {
                            ProfileRow(key: "종교", value: userOption.user_religion!)
                        }
                        if userOption.user_brother != nil {
                            ProfileRow(key: "형제", value: userOption.user_brother!)
                        }
                    }
                    VStack(spacing: 10){
                        if userOption.user_country != nil {
                            ProfileRow(key: "나라", value: userOption.user_country!)
                        }
                        if userOption.user_salary != nil {
                            ProfileRow(key: "수입", value: userOption.user_salary!)
                        }
                        if userOption.user_asset != nil {
                            ProfileRow(key: "재산", value: userOption.user_asset!)
                        }
                        if userOption.user_marriagehistory != nil {
                            ProfileRow(key: "결혼여부", value: userOption.user_marriagehistory!)
                        }
                        if userOption.user_children != nil {
                            ProfileRow(key: "자녀", value: userOption.user_children!)
                        }
                        if userOption.user_marriageplan != nil {
                            ProfileRow(key: "결혼계획", value: userOption.user_marriageplan!)
                        }
                        if userOption.user_childrenplan != nil {
                            ProfileRow(key: "자녀계획", value: userOption.user_childrenplan!)
                        }
                        if userOption.user_parenting != nil {
                            ProfileRow(key: "육아", value: userOption.user_parenting!)
                        }
                        if userOption.user_wishdate != nil {
                            ProfileRow(key: "원하는 데이트", value: userOption.user_wishdate!)
                        }
                        if userOption.user_datecost != nil {
                            ProfileRow(key: "데이트 비용", value: userOption.user_datecost!)
                        }
                    }
                    VStack(spacing: 10){
                        if userOption.user_roommate != nil {
                            ProfileRow(key: "룸메이트", value: userOption.user_roommate!)
                        }
                        if userOption.user_language != nil {
                            ProfileRow(key: "언어", value: userOption.user_language!)
                        }
                    }
                }
                .padding(.vertical, 10)
                
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
                    .padding(.bottom,80)
                }
            }
        }
        .onAppear(){
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
                Text(self.key)
                    .font(.system(size:13))
                    .foregroundColor(Color.gray)
                    .frame(width: 100)
                HStack{
                    Text(self.value)
                        .font(.system(size:13))
                    
                    
                    Spacer()
                }
            }
        }
    }
}
