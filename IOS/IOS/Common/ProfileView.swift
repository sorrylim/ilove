//
//  ProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/07/21.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct ProfileView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var imageList : [UIImage] = []
    @State var isInit : Bool = false
    @State var userId : String
    @State var page : Int = 0
    
    var body: some View {
        VStack{
            ZStack{
                GeometryReader{g in
                    Pager(userId: self.userId , width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.width, page: self.$page, imageList: self.imageList)
                }
            }
            .frame(width : UIScreen.main.bounds.width, height : UIScreen.main.bounds.width)
        }
    }
}


struct ProfileImageList : View {
    
    @State var imageList : [UIImage] = []
    @State var userId : String
    @State var isInit : Bool = false
    
    var body : some View {
        HStack{
            ForEach(self.imageList, id:\.self){ image in
                Image(uiImage: image)
                    .resizable()
                    .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.width)
                
            }
        }
        .onAppear(){
            HttpService.shared.getProfileImageReq(userId: self.userId) { profileImageModelArray in
                for i in 0..<profileImageModelArray.count {
                    KingfisherManager.shared.retrieveImage(with: URL(string: profileImageModelArray[i].image!)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                        self.imageList.append(image!)
                    })
                }
            }
        }
    }
}

