//
//  ChatView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/15.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChatView: View {
    
    @State var room : ChatRoomModel
    
    var body: some View {
        VStack{
            Text("asd")
        }
        .navigationBarTitle(Text("\(self.room.room_title)"))
    }
}
