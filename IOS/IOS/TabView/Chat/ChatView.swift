//
//  ChatView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/15.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChatView: View {
    
    var room : ChatRoomModel
    
    var body: some View {
        VStack{
            Text("asd")
        }
        .navigationBarTitle(Text("\(self.room.room_title)"))
    }
}
struct ChatView_Previews: PreviewProvider {
    static var previews: some View {
        ChatView(room: ChatRoomModel(room_id: "",room_maker: "",room_partner: "",room_title: "",chat_content: "",chat_time: ""))
    }
}

