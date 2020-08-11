    //
    //  ContentView.swift
    //  IOS
    //
    //  Created by 김세현 on 2020/06/19.
    //  Copyright © 2020 KSH. All rights reserved.
    //
    
    import SwiftUI
    
    struct ContentView: View {
        
        static var rootView : ContentView? = nil
        
        @State var title = "아이러브"
        @State var candyShopVisible = false
        
        @State var isLogin = false
        
        var body: some View {
            NavigationView{
                if isLogin==false {
                    StartView()
                }
                else {
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
                            Image("baseline_detail_category_grey_24")
                            Text("스토리")
                        })
                            .tag(2)
                        ChatListView().tabItem({
                            Image("message_icon")
                            Text("대화방")
                        })
                            .tag(3)
                        MyPageView().tabItem({
                            Image("mypage_icon")
                            Text("내 페이지")
                        })
                            .tag(4)
                    }
                    .navigationBarTitle("\(self.title)",displayMode: .inline)
                    .navigationBarItems(trailing: Image("candy_icon").resizable().frame(width: 20, height: 20).onTapGesture {
                        self.candyShopVisible=true
                    })
                }
            }
            .sheet(isPresented: self.$candyShopVisible){
                CandyShopView()
            }
            .onAppear(){
                ContentView.rootView=self
                
                if UserDefaults.standard.string(forKey: "ID")==nil {
                    self.isLogin=false
                }
                else {
                    self.isLogin=true
                }
            }
        }
        
        func setTitle(title: String){
            self.title=title
        }
    }
    
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            ContentView()
        }
    }
