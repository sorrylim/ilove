//
//  ItemManageView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/22.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ItemManageView : View {
    
    @State var myCandy : Int = 0
    @State var vipTicket : Int = 0
    @State var msgTicket : Int = 0
    @State var likeTicket : Int = 0
    
    var body : some View{
        VStack(spacing:20){
            Text("내 사탕 \(myCandy)개")
                .font(.system(size:20))
            Spacer()
            Text("보유 아이템")
                .font(.system(size:20))
            HStack{
                VStack{
                    Image("vip")
                        .resizable()
                        .frame(width:100,height: 100)
                    Text("정회원")
                    Text("\(vipTicket)일")
                        .foregroundColor(Color(red: 255/255, green: 160/255, blue: 0))
                }
                VStack{
                    Image("message_candy")
                        .resizable()
                        .frame(width:100,height: 100)
                    Text("메세지 이용권")
                    Text("\(msgTicket)일")
                        .foregroundColor(Color(red: 255/255, green: 160/255, blue: 0))
                }
                VStack{
                    Image(systemName: "heart.fill")
                        .resizable()
                        .frame(width:100,height: 100)
                        .foregroundColor(Color.red)
                    Text("좋아요")
                    Text("\(likeTicket)개")
                        .foregroundColor(Color(red: 255/255, green: 160/255, blue: 0))
                }
            }
            Spacer()
            HStack(){
                NavigationLink(destination: VipGuideView()){
                    Text("정회원 안내")
                        .font(.system(size: 15,weight: .bold))
                        .foregroundColor(Color.white)
                        .frame(width:140, height:50)
                        .background(Color(red: 255/255, green: 160/255, blue: 0))
                        .cornerRadius(25)
                        .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color(red: 255/255, green: 160/255, blue: 0), lineWidth: 0))
                }
                Spacer()
                //NavigationLink(destination: EditProfileView()){
                    Text("사탕 충전하기")
                        .font(.system(size: 15,weight: .bold))
                        .foregroundColor(Color.white)
                        .frame(width:140, height:50)
                        .background(Color(red: 255/255, green: 160/255, blue: 0))
                        .cornerRadius(25)
                        .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color(red: 255/255, green: 160/255, blue: 0), lineWidth: 0))
                //}
            }
        }
        .padding()
        .navigationBarTitle("아이템 관리")
    }
}

struct ItemManageView_Previews: PreviewProvider {
    static var previews: some View {
        ItemManageView()
    }
}
