//
//  SignUpView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/12.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SignUpView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var phone : String
    
    @State var uiImage : UIImage?
    
    @State var isActive = false
    
    @State var nickname = ""
    @State var birthday = ""
    
    @State var nicknameSelected = false
    @State var birthdaySelected = false
    
    @State var inputImage : UIImage?
    @State var imageUrl : URL?
    
    @State var imagePickerVisiable=false
    @State var introVisible = false
    @State var previewIntroVisible = false
    @State var optionVisible = false
    @State var sheetVisible = false
    
    @State var userOption : UserOptionModel? = UserOptionModel(user_id: "",user_purpose: "", user_gender: "", user_height: "", user_bodytype: "", user_bloodtype: "", user_residence: "", user_job: "", user_education: "", user_holiday: "", user_cigarette: "", user_alcohol: "", user_religion: "", user_brother: "", user_country: "", user_salary: "", user_asset: "", user_marriagehistory: "", user_children: "", user_marriageplan: "", user_childrenplan: "", user_parenting: "", user_wishdate: "", user_datecost: "", user_roommate: "", user_language: "", user_interest: "", user_personality: "", user_favoriteperson: "", user_city: "", user_introduce: "", user_previewintroduce: "")
    
    @State var imageCheck = false
    
    @State var alertVisible = false
    @State var alertMessage = ""
    
    @State var allInsert = false
    
    @State var optionList = ["이용목적","성별","키","체형","혈액형","거주지","직업","학력","휴일","흡연","음주","종교","형제","나라","수입","재산","결혼여부","자녀","결혼계획","자녀계획","원하는 데이트","데이트 비용","룸메이트","언어","취미","성격","내가 좋아하는 사람"]
    @State var optionCount = 0
    
    var body: some View {
        VStack(spacing: 20){
            VStack(spacing: 10){
                if uiImage == nil {
                    Image("default_profile")
                        .resizable()
                        .frame(width: 100,height: 100)
                        .cornerRadius(15)
                        .onTapGesture {
                            self.imagePickerVisiable=true
                            self.introVisible=false
                            self.previewIntroVisible=false
                            self.optionVisible=false
                            self.sheetVisible=true
                    }
                }
                else {
                    Image(uiImage: uiImage!)
                        .resizable()
                        .frame(width: 100, height: 100)
                        .cornerRadius(15)
                        .onTapGesture {
                            self.imagePickerVisiable=true
                            self.introVisible=false
                            self.previewIntroVisible=false
                            self.optionVisible=false
                            self.sheetVisible=true
                    }
                }
                Text("대표사진(필수)")
                    .font(.system(size: 12))
            }
            .padding(.bottom,20)
            
            VStack{
                HStack{
                    Text("닉네임")
                        .font(.system(size: 12))
                    Spacer()
                }
                TextField("닉네임", text: $nickname)
                    .padding(5)
                    .background(
                        Rectangle()
                            .fill(Color.white)
                            .padding(.bottom,2)
                            .background(nicknameSelected ? Color.accentColor : Color(red: 180/255, green: 180/255, blue: 180/255)))
                    .onTapGesture {
                        self.nicknameSelected=true
                        self.birthdaySelected=false
                }
            }
            
            VStack{
                HStack{
                    Text("생년월일")
                        .font(.system(size: 12))
                    Spacer()
                }
                TextField("예) 19950101", text: $birthday)
                    .padding(5)
                    .background(
                        Rectangle()
                            .fill(Color.white)
                            .padding(.bottom,2)
                            .background(birthdaySelected ? Color.accentColor : Color(red: 180/255, green: 180/255, blue: 180/255)))
                    .onTapGesture {
                        self.nicknameSelected=false
                        self.birthdaySelected=true
                }
            }
            
            VStack{
                HStack{
                    Text("한줄소개")
                        .font(.system(size: 12))
                    Spacer()
                }
                HStack{
                    Text((userOption!.user_previewintroduce != "") ? userOption!.user_previewintroduce! : "한줄소개를 입력해주세요.")
                        .font(.system(size:15))
                        .padding(5)
                    Spacer()
                    Image("pencil_icon")
                        .resizable()
                        .frame(width: 15, height: 15)
                }
                .onTapGesture {
                    self.imagePickerVisiable=false
                    self.introVisible=false
                    self.previewIntroVisible=true
                    self.optionVisible=false
                    self.sheetVisible=true
                }
            }
            
            VStack{
                HStack{
                    Text("자기소개")
                        .font(.system(size: 12))
                    Spacer()
                }
                HStack{
                    Text((userOption!.user_introduce != "") ? userOption!.user_introduce! : "자기소개를 입력해주세요.")
                        .font(.system(size:15))
                        .padding(5)
                    Spacer()
                    Image("pencil_icon")
                        .resizable()
                        .frame(width: 15, height: 15)
                }
                .onTapGesture {
                    self.imagePickerVisiable=false
                    self.introVisible=true
                    self.previewIntroVisible=false
                    self.optionVisible=false
                    self.sheetVisible=true
                }
            }
            Spacer()
            Button(action: {
                if !self.imageCheck {
                    self.alertMessage = "프로필 사진은 필수항목입니다."
                }
                else if self.nickname == "" {
                    self.alertMessage = "닉네임을 입력해주세요."
                }
                else if self.birthday == "" {
                    self.alertMessage = "생년월일을 입력해주세요."
                }
                else if self.birthday.count != 8 {
                    self.alertMessage = "생년월일형식을 확인해주세요."
                }
                else if self.userOption!.user_previewintroduce == "" {
                    self.alertMessage = "한줄소개를 입력해주세요."
                }
                else if self.userOption!.user_introduce == "" {
                    self.alertMessage = "자기소개를 입력해주세요."
                }
                else {
                    self.allInsert = true
                    
                    let now = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                    let date=dateFormatter.string(from: now)
                    
                    var table = "user"
                    var col = "user_id, user_nickname, user_birthday, user_authority, user_signdate, user_phone, user_previewintroduce, user_introduce"
                    var values = "'\(self.phone)', '\(self.nickname)', '\(self.birthday)', 'normal', '\(date)', '\(self.phone)', '\(self.userOption!.user_previewintroduce)', '\(self.userOption!.user_introduce)'"
                    
                    HttpService.shared.insertReq(table: table, col: col, values: values){
                        UserInfo.shared.ID=self.phone
                        UserDefaults.standard.set(self.phone, forKey: "ID")
                        
                        HttpService.shared.insertReq(table: "useroption", col: "user_id", values: self.phone){
                            self.imagePickerVisiable=false
                            self.introVisible=false
                            self.previewIntroVisible=false
                            self.optionVisible=true
                            self.sheetVisible = true
                        }
                    }
                    
                    
                }
                
                if !self.allInsert {
                    self.alertVisible = true
                }
            }){
                Text("다음")
                    .font(.system(size: 20))
                    .foregroundColor(.white)
                    .frame(width: UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.07)
            }
            .frame(width: UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.07)
            .background(Color(red: 255/255, green: 160/255, blue: 0/255))
            .cornerRadius(15)
        }
        .navigationBarBackButtonHidden(true)
        .padding(.horizontal,15)
        .padding(.bottom,15)
        .onAppear(){
            self.userOption?.user_id=self.phone
        }
        .sheet(isPresented: $sheetVisible){
            if self.imagePickerVisiable {
                ImagePicker(image: self.$inputImage, imageUrl: self.$imageUrl)
                    .onDisappear(){
                        self.loadImage()
                }
            }
            else if self.previewIntroVisible {
                EditPreviewIntroduceView(userOption: self.$userOption)
            }
            else if self.introVisible {
                EditIntroduceView(userOption: self.$userOption)
            }
            else if self.optionVisible {
                EditUserOptionView(userOption: self.$userOption, title: self.optionList[self.optionCount])
                    .onDisappear(){
                        if self.optionCount < self.optionList.count-1 {
                            self.optionCount=self.optionCount+1
                            self.sheetVisible=true
                        }
                        else {
                            print(self.userOption)
                            ContentView.rootView!.isLogin=true
                            
                        }
                }
            }
        }
        .alert(isPresented: $alertVisible) { () -> Alert in
            Alert(title: Text(self.alertMessage))
        }
    }
    
    func loadImage(){
        guard let inputImage=inputImage else {
            return
        }
        self.uiImage=inputImage
        
        self.imageCheck=true
    }
}
