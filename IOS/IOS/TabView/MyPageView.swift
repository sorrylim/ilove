//
//  MyPageView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct MyPageView : View{
    
    @State var profileImageList : [UIImage] = []
    
    var body: some View{
        //NavigationView{
            ScrollView(){
                HStack(spacing: 15){
                    Spacer()
                    if profileImageList.count > 0 {
                        Image(uiImage: profileImageList[0])
                            .resizable()
                            .frame(width:100,height: 100)
                            .cornerRadius(15)
                    }
                    else {
                        Image(systemName: "default_profile")
                        .resizable()
                        .frame(width:100,height: 100)
                        .cornerRadius(15)
                    }
                    VStack(spacing: 5){
                        Text(UserInfo.shared.NICKNAME)
                            .font(.system(size:20))
                        NavigationLink(destination: EditProfileView(profileImageList: profileImageList)){
                            Text("프로필 편집")
                                .font(.system(size: 15))
                                .foregroundColor(Color.white)
                                .padding(10)
                                .background(Color(red: 255/255, green: 160/255, blue: 0))
                                .cornerRadius(10)
                        }
                    }
                    Spacer()
                }
                .frame(height:200)
                VStack(spacing: 20){
                    NavigationLink(destination: SettingView()){
                        HStack{
                            Image("setting_icon")
                                .resizable()
                                .frame(width:20,height:20)
                            
                            Text("설정")
                                .font(.system(size:15))
                            Spacer()
                        }
                    }
                    .foregroundColor(Color.black)
                    HStack{
                        Image("message_icon")
                            .resizable()
                            .frame(width:20,height:20)
                        Text("1:1 문의")
                            .font(.system(size:15))
                        Spacer()
                    }
                    
                    NavigationLink(destination: ItemManageView()){
                        HStack{
                            Image("baseline_detail_category_grey_24")
                                .resizable()
                                .frame(width:20,height:20)
                            
                            Text("아이템 관리")
                                .font(.system(size:15))
                            Spacer()
                        }
                    }
                    .foregroundColor(Color.black)
                    
                    HStack{
                        Image("kakao_icon")
                            .resizable()
                            .frame(width:20,height:20)
                        Text("카톡으로 초대하기")
                            .font(.system(size:15))
                        Spacer()
                    }
                    HStack{
                        Image("information_icon")
                            .resizable()
                            .frame(width:20,height:20)
                        Text("제휴 안내")
                            .font(.system(size:15))
                        Spacer()
                    }
                }
                .padding(.horizontal,10)
            }
            .padding()
            .onAppear(){
                ContentView.rootView?.setTitle(title: "내 페이지")
                
                HttpService.shared.getProfileImageReq(userId: UserInfo.shared.ID){ profileImageModelArray -> Void in
                    profileImageModelArray.forEach { (profileImageModel) in
                        KingfisherManager.shared.retrieveImage(with: URL(string: profileImageModel.image!)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                            self.profileImageList.append(image!)
                        })
                    }
                    
                }
        }
        //}
    }
}

struct MyPageView_Previews: PreviewProvider {
    static var previews: some View {
        MyPageView()
    }
}
