//
//  MyPageView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MyPageView : View{
    var body: some View{
        //NavigationView{
            ScrollView(){
                HStack{
                    Spacer()
                    Image("default_image")
                        .resizable()
                        .frame(width:100,height: 100)
                    Spacer(minLength:20)
                    VStack{
                        Text("닉네임")
                            .font(.system(size:25))
                        NavigationLink(destination: EditProfileView()){
                            Text("프로필 편집")
                                .font(.system(size: 20,weight: .bold))
                                .foregroundColor(Color.white)
                                .padding()
                                .background(Color.orange)
                                .cornerRadius(25)
                                .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color.orange, lineWidth: 0))
                        }
                    }
                    Spacer()
                }
                .frame(height:200)
                
                NavigationLink(destination: SettingView()){
                    HStack{
                        Image("setting_icon")
                            .resizable()
                            .frame(width:25,height:25)
                        
                        Text("설정")
                            .font(.system(size:20))
                        Spacer()
                    }
                }
                .foregroundColor(Color.black)
                HStack{
                    Image("message_icon")
                        .resizable()
                        .frame(width:25,height:25)
                    Text("1:1 문의")
                        .font(.system(size:20))
                    Spacer()
                }
                
                NavigationLink(destination: ItemManageView()){
                    HStack{
                        Image("baseline_detail_category_grey_24")
                            .resizable()
                            .frame(width:25,height:25)
                        
                        Text("아이템 관리")
                            .font(.system(size:20))
                        Spacer()
                    }
                }
                .foregroundColor(Color.black)
                
                HStack{
                    Image("kakao_icon")
                        .resizable()
                        .frame(width:25,height:25)
                    Text("카톡으로 초대하기")
                        .font(.system(size:20))
                    Spacer()
                }
                HStack{
                    Image("information_icon")
                        .resizable()
                        .frame(width:25,height:25)
                    Text("제휴 안내")
                        .font(.system(size:20))
                    Spacer()
                }
                
            }
            .padding()
            .navigationBarTitle("내 페이지",displayMode: .inline)
        //}
    }
}

struct MyPageView_Previews: PreviewProvider {
    static var previews: some View {
        MyPageView()
    }
}
