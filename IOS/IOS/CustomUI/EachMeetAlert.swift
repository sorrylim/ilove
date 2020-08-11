//
//  EachAlert.swift
//  IOS
//
//  Created by 김세현 on 2020/07/14.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EachMeetAlert: View {
    
    
    
    var userId:String
    var userNickname:String
    var userAge:Int
    var userCity:String
    var uiImage:UIImage
    
    @Binding var showing:Bool
    
    @State var clicked = false
    
    var body: some View {
        VStack(spacing:10){
            Text("연락주세요가 연결되었어요!\n연락처를 열람해보세요")
                .font(.system(size:15))
                .multilineTextAlignment(.center)
            
            Image(uiImage: uiImage)
                .resizable()
                .frame(width: 100, height: 100)
                .clipShape(Circle())
            
            Text("\(userNickname)")
                .font(.system(size:15))
            Text("\(userAge), \(userCity)")
                .font(.system(size:12))
            Button(action: {
                self.clicked=true
            }){
                Text(self.clicked ? self.userId : "연락처 열람하기")
                    .font(.system(size: 15))
                    .foregroundColor(Color.white)
                    .padding(15)
                    .frame(width: 180, height: 40)
                    .background(Color.orange)
                    .cornerRadius(20)
            }
            
            Text("닫기")
                .font(.system(size: 13))
                .foregroundColor(Color.gray)
                .onTapGesture {
                    self.showing=false
            }
            
        }
        .frame(width:230, height: 300)
        .background(Color.white)
        .cornerRadius(20)
        .animation(.spring())
        .onDisappear(){
            self.showing=false
        }
    }
}
