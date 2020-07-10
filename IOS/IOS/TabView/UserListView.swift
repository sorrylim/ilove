//
//  ListView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct UserListView : View{
    
    @State var userList:[UserModel] = []
    
    var body : some View{
        NavigationView{
            List{
                ForEach(userList, id: \.user_id){user in
                    UserRow(user: user)
                }
            }
            .navigationBarTitle("리스트",displayMode: .inline)
            .onAppear(){
                HttpService.shared.getUserListReq(gender: "F", userId: "ksh") { (userModelArray) -> Void in
                    self.userList=userModelArray
                }
            }
        }
        
    }
}
struct UserRow : View{
    
    @State var user : UserModel
    
    @State var uiImage = UIImage()
    
    @State var likeImage: Image = Image(systemName: "heart")
    
    var body : some View{
        HStack(spacing: 20){
            Image(uiImage: self.uiImage)
                .resizable()
                .frame(width: 100, height: 100)
                .cornerRadius(10)
            
            VStack(alignment: .leading, spacing: 10){
                Text("\(user.user_nickname), \(user.user_birthday), \(user.user_city), \(user.user_recentgps)")
                    .font(.system(size : 10))
                    .foregroundColor(.gray)
                if user.user_previewintroduce != nil {
                    Text(user.user_previewintroduce!)
                        .font(.system(size: 15))
                }
                else {
                    Text("")
                        .font(.system(size:15))
                }
                Spacer()
            }
            Spacer()
            VStack{
                Spacer()
                likeImage
                    .foregroundColor(.red)
                    .onAppear(){
                        if self.user.like==1 {
                            self.likeImage=Image(systemName: "heart.fill")
                        }
                        else {
                            self.likeImage=Image(systemName: "heart")
                        }
                }
                .onTapGesture {
                    if self.user.like==1 {
                        print(self.user)
                        HttpService.shared.deleteExpressionReq(userId: "ksh", partnerId: self.user.user_id, expressionType: "like") { (resultModel) -> Void in
                            if resultModel.result=="success" {
                                self.likeImage=Image(systemName: "heart")
                            }
                            self.user.like=0
                        }
                    }
                    else {
                        let now = Date()
                        let dateFormatter = DateFormatter()
                        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                        let date=dateFormatter.string(from: now)
                        
                        HttpService.shared.insertExpressionReq(userId: "ksh", partnerId: self.user.user_id, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                            if resultModel.result=="success" {
                                self.likeImage=Image(systemName: "heart.fill")
                            }
                            else if resultModel.result=="eachsuccess" {
                                self.likeImage=Image(systemName: "heart.fill")
                            }
                            self.user.like=1
                        }
                    }
                }
            }
        }
        .padding(10)
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.user.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
        }
    }
}
