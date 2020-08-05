//
//  StoryView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/07.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct StoryView : View{
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var story : StoryModel
    @State var storyUser = StoryUserModel(user_id: "", user_nickname: "", user_birthday: "", user_recenttime: "", image_content: "", likecount: 0, viewcount: 0, like: 0)
    
    @State var storyImage=UIImage()
    @State var profileImage=UIImage()
    
    @State var likeImage: Image = Image(systemName: "heart")
    
    @State var age=0
    @State var time=""
    var body : some View{
        VStack{
            HStack{
                Image(uiImage: profileImage)
                    .resizable()
                    .frame(width:50,height: 50)
                    .clipShape(Circle())
                VStack(alignment: .leading){
                    Text("\(self.storyUser.user_nickname) \(self.age)")
                    Text("\(self.time)")
                        .font(.system(size:15))
                        .foregroundColor(Color.gray)
                }
                Spacer()
            }
            .padding()
            
            Image(uiImage: self.storyImage)
                .resizable()
                .frame(width: UIScreen.main.bounds.width*0.9, height: UIScreen.main.bounds.width*0.9)
                .cornerRadius(10)
            
            HStack{
                Text("\(self.storyUser.likecount)명이 이 스토리를 좋아합니다.")
                    .font(.system(size : 15))
                    .foregroundColor(Color.gray)
                    .padding(.leading,10)
                Spacer()
                likeImage
                    .resizable()
                    .frame(width:15,height: 15)
                    .foregroundColor(.red)
                    .onTapGesture {
                        if self.storyUser.like==1 {
                            HttpService.shared.deleteStoryExpressionReq(userId: UserInfo.shared.ID, imageId: self.story.image_id) { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.likeImage=Image(systemName: "heart")
                                }
                                self.storyUser.like=0
                                self.storyUser.likecount-=1
                            }
                        }
                        else {
                            let now = Date()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                            let date=dateFormatter.string(from: now)
                            
                            HttpService.shared.insertStoryExpressionReq(userId: UserInfo.shared.ID, imageId: self.story.image_id, expressionDate: date) { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.likeImage=Image(systemName: "heart.fill")
                                }
                                self.storyUser.like=1
                                self.storyUser.likecount+=1
                            }
                        }
                }
                Image("eye_icon")
                    .resizable()
                    .frame(width:15,height: 15)
                Text("\(self.storyUser.viewcount)")
                    .padding(.trailing,10)
            }
            HStack{
                Text("\(self.storyUser.image_content)")
                    .padding(.horizontal,10)
                Spacer()
            }
            Spacer()
        }
        .padding()
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.story.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.storyImage=image!
            })
            
            HttpService.shared.getProfileImageReq(userId: self.story.user_id){ (profileImageModelArray) -> Void in
                KingfisherManager.shared.retrieveImage(with: URL(string: profileImageModelArray[0].image!)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                    self.profileImage=image!
                })
            }
            
            HttpService.shared.getStoryUserReq(userId: UserInfo.shared.ID, imageId: self.story.image_id){ (storyUserModel) -> Void in
                self.storyUser=storyUserModel
                if self.storyUser.like==1 {
                    print("1")
                    self.likeImage=Image(systemName: "heart.fill")
                }
                else {
                    print("0")
                    self.likeImage=Image(systemName: "heart")
                }
                
                var now = Date()
                var dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                let date=dateFormatter.string(from: now)
                
                var storyUserRecentTime = dateFormatter.date(from : self.storyUser.user_recenttime)?.addingTimeInterval(3600 * 9)
                now=now.addingTimeInterval(3600 * 9)
                
                var timeInterval = Int(now.timeIntervalSince(storyUserRecentTime!))
                
                if timeInterval >= 86400 {
                    self.time="\(timeInterval/86400)일 전"
                }
                else if timeInterval >= 3600 {
                    self.time="\(timeInterval/3600)시간 전"
                }
                else if timeInterval >= 60{
                    self.time="\(timeInterval/60)분 전"
                }
                else {
                    self.time="\(timeInterval)초 전"
                }
                
                
                HttpService.shared.insertHistoryReq(userId: UserInfo.shared.ID, partnerId: self.story.user_id, visitType: "story", visitDate: date){ (resultModel) -> Void in
                    
                }
                
                self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(self.storyUser.user_birthday.split(separator: "-")[0]).unsafelyUnwrapped+1)
            }
        }
    }
}
