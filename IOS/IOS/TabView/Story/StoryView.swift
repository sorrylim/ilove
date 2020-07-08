//
//  StoryView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/07.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct StoryView : View{
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var story : StoryModel
    @State var storyUser = StoryUserModel(user_id: "", user_nickname: "", user_birthday: "", user_recentgps: "", image_content: "", likecount: 0, viewcount: 0, like: 0)
    
    @State var likeImage: Image = Image(systemName: "heart")
    
    var body : some View{
        VStack{
            HStack{
                Image(systemName: "pencil")
                    .resizable()
                    .frame(width:20,height: 20)
                VStack(alignment: .leading){
                    Text("\(self.storyUser.user_nickname), \(self.storyUser.user_birthday)")
                    Text("\(self.storyUser.user_recentgps)")
                        .font(.system(size:15))
                        .foregroundColor(Color.gray)
                }
                Spacer()
            }
            .padding()
            Text("\(self.storyUser.image_content)")
            Spacer()
            Image(uiImage: UIImage(data: try! Data(contentsOf: URL(string: self.story.image)!))!)
                .resizable()
                .frame(width: UIScreen.main.bounds.width/2, height: UIScreen.main.bounds.width/2)
                .cornerRadius(10)
            Spacer()
            HStack{
                likeImage
                    .foregroundColor(.red)
                    .onTapGesture {
                        if self.storyUser.like==1 {
                            HttpService.shared.deleteStoryExpressionReq(userId: "ksh", imageId: self.story.image_id) { (resultModel) -> Void in
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
                            
                            HttpService.shared.insertStoryExpressionReq(userId: "ksh", imageId: self.story.image_id, expressionDate: date) { (resultModel) -> Void in
                                if resultModel.result=="success" {
                                    self.likeImage=Image(systemName: "heart.fill")
                                }
                                self.storyUser.like=1
                                self.storyUser.likecount+=1
                            }
                        }
                }
                Text("\(self.storyUser.likecount)")
                Spacer()
                Text("조회수 \(self.storyUser.viewcount)")
            }
            .padding()
            .onAppear(){
                HttpService.shared.getStoryUserReq(userId: "ksh", imageId: self.story.image_id){ (storyUserModel) -> Void in
                    self.storyUser=storyUserModel
                    if self.storyUser.like==1 {
                        print("1")
                        self.likeImage=Image(systemName: "heart.fill")
                    }
                    else {
                        print("0")
                        self.likeImage=Image(systemName: "heart")
                    }
                    
                    let now = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                    let date=dateFormatter.string(from: now)
                    
                    HttpService.shared.insertHistoryReq(userId: "ksh", partnerId: self.story.user_id, visitType: "story", visitDate: date){ (resultModel) -> Void in
                        
                    }
                }
            }
        }
    }
}
