//
//  SettingView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/22.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SettingView : View {
    var body: some View{
        VStack{
            Form(){
                Section(header: Text("알림 및 서비스")){
                    HStack{
                        NavigationLink(destination: AlarmSettingView()){
                            Text("알림 설정")
                                .foregroundColor(Color.black)
                                .font(.system(size:20))
                        }
                        
                        Spacer()
                    }
                    HStack{
                        Text("계정")
                            .font(.system(size:20))
                        Spacer()
                    }
                }
                Section(header: Text("정보")){
                    HStack{
                        Text("버전정보")
                            .font(.system(size:20))
                        Spacer()
                    }
                    HStack{
                        Text("이용약관")
                            .font(.system(size:20))
                        Spacer()
                    }
                    HStack{
                        Text("개인정보 취급방침")
                            .font(.system(size:20))
                        Spacer()
                    }
                }
            }
        }
        .navigationBarTitle("설정")
    }
}

struct SettingView_Previews: PreviewProvider {
    static var previews: some View {
        SettingView()
    }
}
