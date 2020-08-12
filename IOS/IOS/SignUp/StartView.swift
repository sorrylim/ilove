//
//  StartView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/10.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct StartView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var policyVisible = false
    
    var body: some View {
        ZStack{
            VStack{
                Button(action : {
                    self.policyVisible = true
                }){
                    Text("시작하기")
                        .font(.system(size: 30))
                        .foregroundColor(.black)
                }
                .padding(.bottom,100)
            }
            if policyVisible {
                GeometryReader{_ in
                    PolicyAlert(showing: self.$policyVisible)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
        .onDisappear(){
            self.presentationMode.wrappedValue.dismiss()
        }
    }
}

struct StartView_Previews: PreviewProvider {
    static var previews: some View {
        StartView()
    }
}
