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
                        EditProfileRow(key: "키", value: ((userOption.user_height != nil) ? userOption.user_height : "등록 안됨")!)
                        EditProfileRow(key: "체형", value: ((userOption.user_bodytype != nil) ? userOption.user_bodytype : "등록 안됨")!)
                        EditProfileRow(key: "혈액형", value: ((userOption.user_bloodtype != nil) ? userOption.user_bloodtype : "등록 안됨")!)
                        EditProfileRow(key: "직업", value: ((userOption.user_job != nil) ? userOption.user_job : "등록 안됨")!)
                        EditProfileRow(key: "학력", value: ((userOption.user_education != nil) ? userOption.user_education : "등록 안됨")!)
                        EditProfileRow(key: "휴일", value: ((userOption.user_holiday != nil) ? userOption.user_holiday : "등록 안됨")!)
                        EditProfileRow(key: "흡연", value: ((userOption.user_cigarette != nil) ? userOption.user_cigarette : "등록 안됨")!)
                        EditProfileRow(key: "음주", value: ((userOption.user_alcohol != nil) ? userOption.user_cigarette : "등록 안됨")!)
                        EditProfileRow(key: "종교", value: ((userOption.user_religion != nil) ? userOption.user_religion : "등록 안됨")!)
                        EditProfileRow(key: "형제", value: ((userOption.user_brother != nil) ? userOption.user_brother : "등록 안됨")!)
                    }
                    VStack(spacing: 10){
                        EditProfileRow(key: "나라", value: ((userOption.user_country != nil) ? userOption.user_country : "등록 안됨")!)
                        EditProfileRow(key: "수입", value: ((userOption.user_salary != nil) ? userOption.user_salary : "등록 안됨")!)
                        EditProfileRow(key: "재산", value: ((userOption.user_asset != nil) ? userOption.user_asset : "등록 안됨")!)
                        EditProfileRow(key: "결혼여부", value: ((userOption.user_marriagehistory != nil) ? userOption.user_marriagehistory : "등록 안됨")!)
                        EditProfileRow(key: "자녀", value: ((userOption.user_children != nil) ? userOption.user_children : "등록 안됨")!)
                        EditProfileRow(key: "결혼계획", value: ((userOption.user_marriageplan != nil) ? userOption.user_marriageplan : "등록 안됨")!)
                        EditProfileRow(key: "자녀계획", value: ((userOption.user_childrenplan != nil) ? userOption.user_childrenplan : "등록 안됨")!)
                        EditProfileRow(key: "육아", value: ((userOption.user_parenting != nil) ? userOption.user_parenting : "등록 안됨")!)
                        EditProfileRow(key: "원하는 데이트", value: ((userOption.user_wishdate != nil) ? userOption.user_wishdate : "등록 안됨")!)
                        EditProfileRow(key: "데이트 비용", value: ((userOption.user_datecost != nil) ? userOption.user_datecost : "등록 안됨")!)
                    }
                    VStack(spacing: 10){
                        EditProfileRow(key: "룸메이트", value: ((userOption.user_roommate != nil) ? userOption.user_roommate : "등록 안됨")!)
                        EditProfileRow(key: "언어", value: ((userOption.user_language != nil) ? userOption.user_language : "등록 안됨")!)
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
