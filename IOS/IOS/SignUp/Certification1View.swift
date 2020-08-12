//
//  Certification1View.swift
//  IOS
//
//  Created by 김세현 on 2020/08/11.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct Certification1View: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var content = ""
    @State var selected = false
    
    var body: some View {
        VStack(spacing: 40){
            HStack{
                Text("서비스를 이용하기 위해\n핸드폰인증이 필요합니다.")
                    .font(.system(size: 25))
                Spacer()
            }
            VStack(spacing: 10){
                TextField("'-'제외 휴대폰번호 입력",text: $content)
                    .font(.system(size: 25))
                    .multilineTextAlignment(.center)
                    .background(
                        Rectangle()
                            .fill(Color.white)
                            .padding(.bottom,2)
                            .background(selected ? Color.accentColor : Color(red: 180/255, green: 180/255, blue: 180/255)))
                    .padding(2)
                    .onTapGesture {
                        self.selected=true
                }
                HStack{
                    Spacer()
                    NavigationLink(destination: Certification2View(phone: self.content)) {
                        Text("보내기")
                            .font(.system(size: 20))
                            .foregroundColor(content.count != 11 ? .gray : .black)
                    }
                    .disabled(content.count != 11)
                }
            }
            Spacer()
        }
        .padding()
        .navigationBarBackButtonHidden(true)
    }
}

struct Certification1View_Previews: PreviewProvider {
    static var previews: some View {
        Certification1View()
    }
}
