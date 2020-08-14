//
//  Certification2View.swift
//  IOS
//
//  Created by 김세현 on 2020/08/11.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct Certification2View: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var phone : String
    
    @State var selected = false
    @State var content = ""
    @State var certifyNum : Float?
    
    @State var isActive = false
    var body: some View {
        VStack(spacing: 40){
            HStack{
                Text("인증번호를\n입력해주세요")
                    .font(.system(size: 25))
                Spacer()
            }
            VStack(spacing: 10){
                TextField("인증번호 6자리",text: $content)
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
                    NavigationLink(destination: SignUpView(phone: self.phone), isActive: self.$isActive){
                        Text("보내기")
                            .font(.system(size: 20))
                            .foregroundColor(content.count != 6 ? .gray : .black)
                            .onTapGesture {
                                if self.content == String(Int(self.certifyNum!)) {
                                    self.isActive = true
                                }
                                else {
                                    self.isActive = false
                                }
                        }
                    }
                    .disabled(content.count != 6)
                }
            }
            Spacer()
        }
        .navigationBarBackButtonHidden(true)
        .padding()
        .onAppear(){
            self.certifyNum = Float.random(in: 0..<10) * 100000
            HttpService.shared.sendSMSReq(phone: self.phone, certifyNum: Int(self.certifyNum!))
        }
        .onDisappear(){
            self.presentationMode.wrappedValue.dismiss()
        }
    }
}
