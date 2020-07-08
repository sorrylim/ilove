    //
    //  ContentView.swift
    //  IOS
    //
    //  Created by 김세현 on 2020/06/19.
    //  Copyright © 2020 KSH. All rights reserved.
    //
    
    import SwiftUI
    
    struct ContentView: View {
        var body: some View {
            TabView{
                ChannelView().tabItem({
                    Text("채널")
                })
                    .tag(0)
                UserListView().tabItem({
                    Image(systemName: "list.dash")
                    Text("리스트")
                })
                    .tag(1)
                StoryListView().tabItem({
                    Text("스토리")
                })
                    .tag(2)
                ChatListView().tabItem({
                    Text("대화방")
                })
                    .tag(3)
                MyPageView().tabItem({
                    Text("내 페이지")
                })
                    .tag(4)
            }
        }
    }
    
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            ContentView()
        }
    }
