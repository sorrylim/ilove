//
//  ChatRoomView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChatListView : View{
    var body: some View{
        VStack{
            Text("대화방")
        }
        .onAppear(){
            ContentView.rootView?.setTitle(title: "대화방")
        }
    }
}
