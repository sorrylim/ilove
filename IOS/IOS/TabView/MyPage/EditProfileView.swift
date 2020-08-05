//
//  EditProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditProfileView : View{
    
    @State var profileImageList : [UIImage] = []
    
    @State var userOption : UserOptionModel = UserOptionModel(user_id: UserInfo.shared.ID, user_height: nil, user_bodytype: nil, user_bloodtype: nil, user_residence: nil, user_job: nil, user_education: nil, user_holiday: nil, user_cigarette: nil, user_alcohol: nil, user_religion: nil, user_brother: nil, user_country: nil, user_salary: nil, user_asset: nil, user_marriagehistory: nil, user_children: nil,user_marriageplan: nil, user_childrenplan: nil, user_parenting: nil, user_wishdate: nil, user_datecost: nil, user_roommate: nil, user_language: nil, user_interest: nil, user_personality: nil, user_favoriteperson: nil, user_city: nil, user_introduce: nil, user_previewintroduce: nil, user_gender: nil)
    
    var body: some View{
        ScrollView{
            Group{
                VStack{
                    Image("default_image")
                        .resizable()
                        .frame(width:100, height:100)
                    Text("대표 사진")
                        .font(.system(size: 10))
                        .foregroundColor(.gray)
                }
                
                HStack{
                    Image("default_image")
                        .resizable()
                        .frame(width:100, height:100)
                    Image("default_image")
                        .resizable()
                        .frame(width:100, height:100)
                    Image("default_image")
                        .resizable()
                        .frame(width:100, height:100)
                }
            }
            .padding(.vertical,10)
            
            Group{
                VStack{
                    HStack{
                        Text("한줄소개")
                            .font(.system(size: 15))
                        Spacer()
                    }
                    if self.userOption.user_previewintroduce != nil {
                        HStack{
                            Text(self.userOption.user_previewintroduce!)
                                .font(.system(size: 13))
                                .foregroundColor(.gray)
                                .lineLimit(5)
                            Spacer()
                            Image("pencil_icon")
                                .resizable()
                                .frame(width: 15, height: 15)
                        }
                    }
                }
                .padding(.horizontal,10)
                VStack{
                    HStack{
                        Text("자기소개")
                            .font(.system(size: 15))
                        Spacer()
                    }
                    if self.userOption.user_introduce != nil {
                        HStack{
                            Text(self.userOption.user_introduce!)
                                .font(.system(size: 13))
                                .foregroundColor(.gray)
                                .lineLimit(5)
                            Spacer()
                            Image("pencil_icon")
                                .resizable()
                                .frame(width: 15, height: 15)
                        }
                    }
                }
                .padding(.horizontal,10)
            }
            .padding(.vertical,10)
            
            Group{
                VStack(spacing: 10){
                    if userOption.user_height != nil {
                        EditProfileRow(key: "키", value: userOption.user_height!)
                    }
                    if userOption.user_bodytype != nil {
                        EditProfileRow(key: "체형", value: userOption.user_bodytype!)
                    }
                    if userOption.user_bloodtype != nil {
                        EditProfileRow(key: "혈액형", value: userOption.user_bloodtype!)
                    }
                    if userOption.user_job != nil {
                        EditProfileRow(key: "직업", value: userOption.user_job!)
                    }
                    if userOption.user_education != nil {
                        EditProfileRow(key: "학력", value: userOption.user_education!)
                    }
                    if userOption.user_holiday != nil {
                        EditProfileRow(key: "휴일", value: userOption.user_holiday!)
                    }
                    if userOption.user_cigarette != nil {
                        EditProfileRow(key: "담배", value: userOption.user_cigarette!)
                    }
                    if userOption.user_alcohol != nil {
                        EditProfileRow(key: "음주", value: userOption.user_alcohol!)
                    }
                    if userOption.user_religion != nil {
                        EditProfileRow(key: "종교", value: userOption.user_religion!)
                    }
                    if userOption.user_brother != nil {
                        EditProfileRow(key: "형제", value: userOption.user_brother!)
                    }
                }
                VStack(spacing: 10){
                    if userOption.user_country != nil {
                        EditProfileRow(key: "나라", value: userOption.user_country!)
                    }
                    if userOption.user_salary != nil {
                        EditProfileRow(key: "수입", value: userOption.user_salary!)
                    }
                    if userOption.user_asset != nil {
                        EditProfileRow(key: "재산", value: userOption.user_asset!)
                    }
                    if userOption.user_marriagehistory != nil {
                        EditProfileRow(key: "결혼여부", value: userOption.user_marriagehistory!)
                    }
                    if userOption.user_children != nil {
                        EditProfileRow(key: "자녀", value: userOption.user_children!)
                    }
                    if userOption.user_marriageplan != nil {
                        EditProfileRow(key: "결혼계획", value: userOption.user_marriageplan!)
                    }
                    if userOption.user_childrenplan != nil {
                        EditProfileRow(key: "자녀계획", value: userOption.user_childrenplan!)
                    }
                    if userOption.user_parenting != nil {
                        EditProfileRow(key: "육아", value: userOption.user_parenting!)
                    }
                    if userOption.user_wishdate != nil {
                        EditProfileRow(key: "원하는 데이트", value: userOption.user_wishdate!)
                    }
                    if userOption.user_datecost != nil {
                        EditProfileRow(key: "데이트 비용", value: userOption.user_datecost!)
                    }
                }
                .padding(.top,10)
                VStack(spacing: 10){
                    if userOption.user_roommate != nil {
                        EditProfileRow(key: "룸메이트", value: userOption.user_roommate!)
                    }
                    if userOption.user_language != nil {
                        EditProfileRow(key: "언어", value: userOption.user_language!)
                    }
                }
                .padding(.top,10)
            }
            .padding(.vertical,10)
        }
        .padding(10)
        .onAppear(){
            HttpService.shared.getUserOptionReq(userId: UserInfo.shared.ID){ userOptionModel -> Void in
                self.userOption=userOptionModel
                
                print(self.userOption)
            }
        }
    }
}

struct EditProfileRow : View{
    
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
                    Spacer()
                    Text(self.value)
                        .font(.system(size:13))
                        .foregroundColor(Color(red: 255/255, green: 160/255, blue: 0/255))
                }
            }
        }
    }
}
