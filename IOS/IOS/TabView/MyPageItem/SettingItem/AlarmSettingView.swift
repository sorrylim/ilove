//
//  AlarmSettingView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/22.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct AlarmSettingView : View {
    
    @State var phoneToggle : Bool = false
    @State var updateProfileToggle : Bool = true
    @State var checkProfileToggle : Bool = true
    @State var likeToggle : Bool = true
    @State var messageToggle : Bool = true
    @State var meetToggle : Bool = true
    
    var body: some View{
        Form(){
            Section(header: Text("알람 설정")){
                Toggle(isOn: $phoneToggle){
                    Text("지정된 연락처 만나지 않기")
                        .font(.system(size:20))
                        .foregroundColor(Color.black)
                }
                Toggle(isOn: $updateProfileToggle){
                    Text("프로필 올리기 알림")
                        .font(.system(size:20))
                        .foregroundColor(Color.black)
                }
                Toggle(isOn: $checkProfileToggle){
                    Text("프로필 확인 알림")
                        .font(.system(size:20))
                        .foregroundColor(Color.black)
                }
                Toggle(isOn: $likeToggle){
                    Text("좋아요 알림")
                        .font(.system(size:20))
                        .foregroundColor(Color.black)
                }
                Toggle(isOn: $messageToggle){
                    Text("메세지 알림")
                        .font(.system(size:20))
                        .foregroundColor(Color.black)
                }
                Toggle(isOn: $meetToggle){
                    Text("만나봐요 알림")
                        .font(.system(size:20))
                        .foregroundColor(Color.black)
                }
            }
        }
    }
}

struct AlarmSettingView_Previews: PreviewProvider {
    static var previews: some View {
        AlarmSettingView()
    }
}
