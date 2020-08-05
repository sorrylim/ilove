//
//  EditPreviewIntroduceView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditIntroduceView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @Binding var userOption : UserOptionModel?
    
    @State var alertVisible = false
    @State var content = ""
    
    var body: some View {
        VStack(spacing: 30){
            HStack{
                Spacer()
                Text("자기소개(30자 이상)")
                    .font(.system(size: 15))
                Spacer()
                Image("baseline_rightarrow_grey_24")
                    .onTapGesture {
                        if self.content.count<30 {
                            self.alertVisible=true
                        }
                        else{
                            HttpService.shared.updateIntroduce(userId: UserInfo.shared.ID, introduceType: "user_introduce", introduce: self.content){
                                self.userOption!.user_introduce=self.content
                                self.presentationMode.wrappedValue.dismiss()
                                
                            }
                        }
                }
            }
            VStack{
                MultilineTextField(text: "자신을 소개해보세요!", content: $content)
                    .multilineTextAlignment(.center)
                    
            }
            Spacer()
        }
        .padding(.vertical,10)
        .padding(.horizontal,20)
        .alert(isPresented: $alertVisible){
            Alert(title: Text("30자 이상 입력해주세요."))
        }
    }
}
