//
//  EditProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditProfileView : View{
    
    @State var profileImageList : [UIImage]
    
    @State var userOption : UserOptionModel? = nil
    
    @State var sheetVisible = false
    @State var previewIntroVisible = false
    @State var introVisible = false
    
    @State var isInit = false
    
    var body: some View{
        ScrollView{
            if isInit {
                Group{
                    VStack{
                        Image(uiImage: profileImageList[0])
                            .resizable()
                            .frame(width:100, height:100)
                        Text("대표 사진(필수)")
                            .font(.system(size: 10))
                            .foregroundColor(.gray)
                            .cornerRadius(10)
                    }
                    HStack{
                        ForEach(1..<4, id:\.self) { i in
                            ZStack{
                                if i<self.profileImageList.count {
                                    Image(uiImage: self.profileImageList[i])
                                        .resizable()
                                        .frame(width:100, height:100)
                                        .cornerRadius(10)
                                }
                                else {
                                    Image("default_image")
                                        .resizable()
                                        .frame(width:100, height:100)
                                        .cornerRadius(10)
                                }
                            }
                        }
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
                        HStack{
                            Text(((userOption!.user_previewintroduce != nil) ? userOption?.user_previewintroduce : "등록 안됨")!)
                                .font(.system(size: 13))
                                .foregroundColor(.gray)
                                .lineLimit(5)
                            Spacer()
                            Image("pencil_icon")
                                .resizable()
                                .frame(width: 15, height: 15)
                                .onTapGesture {
                                    self.introVisible=false
                                    self.sheetVisible=true
                                    self.previewIntroVisible=true
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
                        HStack{
                            Text(((userOption!.user_introduce != nil) ? userOption?.user_introduce : "등록 안됨")!)
                                .font(.system(size: 13))
                                .foregroundColor(.gray)
                                .lineLimit(5)
                            Spacer()
                            Image("pencil_icon")
                                .resizable()
                                .frame(width: 15, height: 15)
                                .onTapGesture {
                                    self.previewIntroVisible=false
                                    self.sheetVisible=true
                                    self.introVisible=true
                            }
                        }
                    }
                    .padding(.horizontal,10)
                }
                .padding(.vertical,10)
                
                VStack{
                    VStack(spacing: 10){
                        EditProfileRow(key: "키", value: ((userOption!.user_height != nil) ? userOption?.user_height : "등록 안됨")!)
                        EditProfileRow(key: "체형", value: ((userOption!.user_bodytype != nil) ? userOption?.user_bodytype : "등록 안됨")!)
                        EditProfileRow(key: "혈액형", value: ((userOption!.user_bloodtype != nil) ? userOption?.user_bloodtype : "등록 안됨")!)
                        EditProfileRow(key: "직업", value: ((userOption!.user_job != nil) ? userOption?.user_job : "등록 안됨")!)
                        EditProfileRow(key: "학력", value: ((userOption!.user_education != nil) ? userOption?.user_education : "등록 안됨")!)
                        EditProfileRow(key: "휴일", value: ((userOption!.user_holiday != nil) ? userOption?.user_holiday : "등록 안됨")!)
                        EditProfileRow(key: "흡연", value: ((userOption!.user_cigarette != nil) ? userOption?.user_cigarette : "등록 안됨")!)
                        EditProfileRow(key: "음주", value: ((userOption!.user_alcohol != nil) ? userOption?.user_cigarette : "등록 안됨")!)
                        EditProfileRow(key: "종교", value: ((userOption!.user_religion != nil) ? userOption?.user_religion : "등록 안됨")!)
                        EditProfileRow(key: "형제", value: ((userOption!.user_brother != nil) ? userOption?.user_brother : "등록 안됨")!)
                    }
                    VStack(spacing: 10){
                        EditProfileRow(key: "나라", value: ((userOption!.user_country != nil) ? userOption?.user_country : "등록 안됨")!)
                        EditProfileRow(key: "수입", value: ((userOption!.user_salary != nil) ? userOption?.user_salary : "등록 안됨")!)
                        EditProfileRow(key: "재산", value: ((userOption!.user_asset != nil) ? userOption?.user_asset : "등록 안됨")!)
                        EditProfileRow(key: "결혼여부", value: ((userOption!.user_marriagehistory != nil) ? userOption?.user_marriagehistory : "등록 안됨")!)
                        EditProfileRow(key: "자녀", value: ((userOption!.user_children != nil) ? userOption?.user_children : "등록 안됨")!)
                        EditProfileRow(key: "결혼계획", value: ((userOption!.user_marriageplan != nil) ? userOption?.user_marriageplan : "등록 안됨")!)
                        EditProfileRow(key: "자녀계획", value: ((userOption!.user_childrenplan != nil) ? userOption?.user_childrenplan : "등록 안됨")!)
                        EditProfileRow(key: "육아", value: ((userOption!.user_parenting != nil) ? userOption?.user_parenting : "등록 안됨")!)
                        EditProfileRow(key: "원하는 데이트", value: ((userOption!.user_wishdate != nil) ? userOption?.user_wishdate : "등록 안됨")!)
                        EditProfileRow(key: "데이트 비용", value: ((userOption!.user_datecost != nil) ? userOption?.user_datecost : "등록 안됨")!)
                    }
                    VStack(spacing: 10){
                        EditProfileRow(key: "룸메이트", value: ((userOption!.user_roommate != nil) ? userOption?.user_roommate : "등록 안됨")!)
                        EditProfileRow(key: "언어", value: ((userOption!.user_language != nil) ? userOption?.user_language : "등록 안됨")!)
                    }
                }
                .padding(.vertical,10)
                .padding(.horizontal,10)
            }
        }
        .padding(10)
        .sheet(isPresented: $sheetVisible){
            if self.previewIntroVisible {
                EditPreviewIntroduceView(userOption: self.$userOption)
            }
            else if self.introVisible {
                EditIntroduceView(userOption: self.$userOption)
            }
        }
        .onAppear(){
            HttpService.shared.getUserOptionReq(userId: UserInfo.shared.ID){ userOptionModel -> Void in
                self.userOption=userOptionModel
                
                self.isInit=true
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
                HStack{
                    Text(self.key)
                        .font(.system(size:13))
                        .foregroundColor(Color.gray)
                    Spacer()
                    Text(self.value)
                        .font(.system(size:13))
                        .foregroundColor(Color(red: 255/255, green: 160/255, blue: 0/255))
                    Image("baseline_rightarrow_grey_24")
                        .resizable()
                        .frame(width: 15, height: 15)
                }
            }
        }
    }
}
