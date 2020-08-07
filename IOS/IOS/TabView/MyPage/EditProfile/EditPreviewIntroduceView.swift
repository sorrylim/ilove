//
//  EditPreviewIntroduceView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditPreviewIntroduceView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @Binding var userOption : UserOptionModel?
    
    @State var alertVisible = false
    @State var content = ""
    
    var body: some View {
        VStack{
            HStack{
                Spacer()
                Text("한줄소개")
                    .font(.system(size: 15))
                Spacer()
                Image("baseline_rightarrow_grey_24")
                    .onTapGesture {
                        if self.content.count<5 {
                            self.alertVisible=true
                        }
                        else{
                            HttpService.shared.updateIntroduceReq(userId: UserInfo.shared.ID, introduceType: "user_previewintroduce", introduce: self.content){
                                self.userOption!.user_previewintroduce=self.content
                                self.presentationMode.wrappedValue.dismiss()
                                
                            }
                        }
                }
            }
            Spacer()
            VStack{
                Text("한줄로 자신을 표현해주세요!")
                    .font(.system(size: 20))
                Text("(5자 이상)")
                    .font(.system(size: 15))
                    .foregroundColor(.gray)
                TextField("이곳에 한줄소개를 입력해주세요.", text: $content)
                    .multilineTextAlignment(.center)
                    
            }
            Spacer()
        }
        .padding(.vertical,10)
        .padding(.horizontal,20)
        .alert(isPresented: $alertVisible){
            Alert(title: Text("5자 이상 입력해주세요."))
        }
    }
}
