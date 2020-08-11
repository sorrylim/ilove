//
//  StartView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/10.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct StartView: View {
    var body: some View {
        VStack{
            Button(action : {
                
            }){
                Text("시작하기")
                    .font(.system(size: 30))
                    .foregroundColor(.black)
            }
            .padding(.bottom,100)
        }
    }
}

struct StartView_Previews: PreviewProvider {
    static var previews: some View {
        StartView()
    }
}
