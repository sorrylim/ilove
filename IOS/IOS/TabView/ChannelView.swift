//
//  ChannelView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI


struct ChannelView : View{
    
    @State var showModal = false
    
    @State var profileCount : Int = -1
    @State var storyCount : Int = -1
    
    @State var sendLikeCount : Int = -1
    @State var receiveLikeCount : Int = -1
    @State var eachLikeCount : Int = -1
    
    @State var sendMeetCount : Int = -1
    @State var receiveMeetCount : Int = -1
    @State var eachMeetCount : Int = -1
    
    var body: some View {
        NavigationView {
            ScrollView(){
                //아이러브 신규 이성
                Group{
                    HStack{
                        Text("아이러브 신규 이성")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    
                    VStack(spacing: 20){
                        HStack{
                            Spacer()
                            Image(systemName: "circle")
                                .resizable()
                                .frame(width: 80,height: 80)
                            Spacer()
                            Image(systemName: "circle")
                                .resizable()
                                .frame(width: 80,height: 80)
                            Spacer()
                        }
                        HStack{
                            Spacer()
                            Image(systemName: "circle")
                                .resizable()
                                .frame(width: 80,height: 80)
                            Spacer()
                            Image(systemName: "circle")
                                .resizable()
                                .frame(width: 80,height: 80)
                            Spacer()
                        }
                    }
                    
                    Spacer(minLength: 30)
                }
                
                //내 프로필 확인한 사람 & 내 스토리를 확인한 사람
                Group{
                    HStack{
                        Text("향기를 남기고 간 그대는")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    Spacer(minLength: 10)
                    VStack(spacing: 20){
                        NavigationLink(destination: PartnerView(title : "내 프로필을 확인한 사람")){
                            HStack{
                                Text("내 프로필을 확인한 사람")
                                    .font(.system(size: 15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(profileCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                            
                        NavigationLink(destination: PartnerView(title : "내 스토리를 확인한 사람")){
                            HStack{
                                Text("내 스토리를 확인한 사람")
                                    .font(.system(size: 15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(storyCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                    }
                    .padding()
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.orange,lineWidth: 2))
                    Spacer(minLength: 30)
                }
                
                Group{
                    HStack{
                        Image(systemName: "circle")
                            .frame(width:60,height:60)
                        VStack(alignment: .leading){
                            Text("내 프로필 상단으로 올리기")
                                .font(.system(size:20))
                            Text("많은 이성들에게 내 프로필을 보여주세요")
                                .font(.system(size:15))
                                .foregroundColor(Color.gray)
                        }
                        Spacer()
                    }
                    Spacer(minLength: 30)
                }
                
                Group{
                    HStack{
                        Text("아이러브 좋아요")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    VStack(spacing: 20){
                        NavigationLink(destination: PartnerView(title: "내가 좋아요를 보낸 이성")){
                            HStack{
                                Text("내가 좋아요를 보낸 이성")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(sendLikeCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                    
                        NavigationLink(destination: PartnerView(title: "나에게 좋아요를 보낸 이성")){
                            HStack{
                                Text("나에게 좋아요를 보낸 이성")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(receiveLikeCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                        
                        NavigationLink(destination: PartnerView(title: "서로 좋아요가 연결된 이성")){
                            HStack{
                                Text("서로 좋아요가 연결된 이성")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(eachLikeCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                    }
                    .padding()
                    Spacer(minLength: 30)
                }
                
                Group{
                    HStack{
                        Text("우리 만나봐요!")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    VStack(spacing: 20){
                        
                        NavigationLink(destination: PartnerView(title : "내가 만나고 싶은 그대")){
                            HStack{
                                Text("내가 만나고 싶은 그대")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(sendMeetCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                        
                        
                        NavigationLink(destination: PartnerView(title : "나를 만나고 싶어하는 그대")){
                            HStack{
                                Text("나를 만나고 싶어하는 그대")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(receiveMeetCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                        
                        NavigationLink(destination: PartnerView(title : "서로 연락처를 주고받은 그대")){
                            HStack{
                                Text("서로 연락처를 주고받은 그대")
                                    .font(.system(size:15))
                                    .foregroundColor(Color.gray)
                                Spacer()
                                Text("\(eachMeetCount)명")
                                    .foregroundColor(Color.black)
                            }
                        }
                    }
                    .padding()
                    Spacer(minLength: 30)
                }
                
            }
            .padding()
            .navigationBarTitle("채널",displayMode: .inline)
            .onAppear{
                
                HttpService.shared.getViewCountReq(userId: "hyeha",callback: { (data) -> Void in
                    self.profileCount=data.profile
                    self.storyCount=data.story
                })
                
                HttpService.shared.getExpressionCountReq(userId: "hyeha", callback: { (data) -> Void in
                    self.sendLikeCount=data.send_like
                    self.receiveLikeCount=data.receive_like
                    self.eachLikeCount=data.each_like
                    
                    self.sendMeetCount=data.send_meet
                    self.receiveMeetCount=data.receive_meet
                    self.eachMeetCount=data.each_meet
                })
            }
        }
    }
}

struct ChannelView_Previews: PreviewProvider {
    static var previews: some View {
        ChannelView()
    }
}
