//
//  VipGuideView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/22.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct VipGuideView : View {
    var body: some View{
        ScrollView(){
            Text("정회원님만의 무료혜택!")
                .font(.system(size:25))
            HStack{
                Image(systemName: "pencil.circle")
                    .frame(width:100,height: 100)
                Spacer()
                VStack(alignment: .leading){
                    Spacer()
                    Text("대화 무제한")
                        .font(.system(size:15,weight:.bold))
                    Spacer()
                    Text("좋아요가 연결된 상대와는 어떠한 제한도 없이 무제한 메세지 이용이 가능합니다 마음껏 채팅을 즐겨보세요!")
                        .font(.system(size:15))
                        .foregroundColor(Color.gray)
                    Spacer()
                }
                Spacer()
            }
            HStack{
                Image(systemName: "pencil.circle")
                    .frame(width:100,height: 100)
                Spacer()
                VStack(alignment: .leading){
                    Spacer()
                    Text("프로필 열람 무제한")
                        .font(.system(size:15,weight:.bold))
                    Spacer()
                    Text("아이러브 모든 유저의 상세프로필을 열람할 수 있습니다. 당신의 스타일을 찾아 연락해보세요!")
                        .font(.system(size:15))
                        .foregroundColor(Color.gray)
                    Spacer()
                }
            }
            HStack{
                Image(systemName: "pencil.circle")
                    .frame(width:100,height: 100)
                Spacer()
                VStack(alignment: .leading){
                    Spacer()
                    Text("매일 좋아요 5개 지급")
                        .font(.system(size:15,weight:.bold))
                    Spacer()
                    Text("매일마다 지급되는 5개의 좋아요를 잘 활용하여 매칭확률을 높이세요!")
                        .font(.system(size:15))
                        .foregroundColor(Color.gray)
                    Spacer()
                }
                Spacer()
            }
            HStack{
                Image(systemName: "pencil.circle")
                    .frame(width:100,height: 100)
                Spacer()
                VStack(alignment: .leading){
                    Spacer()
                    Text("매일 만나봐요 1개 지급")
                        .font(.system(size:15,weight:.bold))
                    Spacer()
                    Text("매일마다 지급되는 만나봐요를 잘 활용하여 미팅확률을 높이세요!")
                        .font(.system(size:15))
                        .foregroundColor(Color.gray)
                    Spacer()
                }
                Spacer()
            }
            Spacer()
            NavigationLink(destination: VipGuideView()){
                Text("정회원 신청하기")
                    .font(.system(size: 15,weight: .bold))
                    .foregroundColor(Color.white)
                    .frame(width:150, height:50)
                    .background(Color.orange)
                    .cornerRadius(25)
                    .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color.orange, lineWidth: 0))
            }
            Spacer()
        }
        .padding()
        .navigationBarTitle("정회원 안내")
    }
}

struct VipGuideView_Previews: PreviewProvider {
    static var previews: some View {
        VipGuideView()
    }
}
