//
//  StoryView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct StoryView : View {
    var body: some View{
        NavigationView{
            ScrollView(){
                HStack{
                    Image(systemName: "circle")
                        .frame(width:100,height: 100)
                    Spacer()
                    VStack{
                        NavigationLink(destination: WriteStoryView()) {
                            Text("스토리 작성하기")
                                .font(.system(size: 20,weight: .bold))
                                .frame(width:200)
                                .foregroundColor(Color.white)
                                .padding()
                                .background(Color.orange)
                                .cornerRadius(15)
                                .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color.orange, lineWidth: 0))
                        }
                        Text("자신만의 일상스토리로 멋내 보세요")
                            .font(.system(size: 15))
                            .foregroundColor(Color.gray)
                    }
                    Spacer()
                }
            }
            .padding()
            .navigationBarTitle("스토리",displayMode: .inline)
        }
    }
}

struct StoryView_Previews: PreviewProvider {
    static var previews: some View {
        StoryView()
    }
}
