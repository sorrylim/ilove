//
//  StoryView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct StoryListView : View {
    
    @State var writeVisible=false
    
    @State var rows:Int=0
    @State var total:Int=0
    
    @State var storyList:[StoryModel]=[]
    @State var myStoryImage:UIImage=UIImage()
    
    var body: some View{
        //NavigationView{
            VStack{
                HStack{
                    Image(uiImage: self.myStoryImage)
                        .resizable()
                        .frame(width:120,height: 120)
                        .cornerRadius(10)
                    Spacer()
                    VStack{
                        Spacer()
                        Button(action: {
                            self.writeVisible=true
                        }) {
                            Text("스토리 작성하기")
                                .font(.system(size: 15,weight: .bold))
                                .frame(width:200)
                                .foregroundColor(Color.white)
                                .padding()
                                .background(Color.orange)
                                .cornerRadius(15)
                                .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color.orange, lineWidth: 0))
                        }
                        Spacer(minLength:5)
                        Text("자신만의 일상스토리로 멋내 보세요")
                            .font(.system(size: 15))
                            .foregroundColor(Color.gray)
                        Spacer()
                    }
                    Spacer()
                }
                .frame(height:120)
                .padding()
                
                VStack(spacing: 0){
                    ForEach(0..<self.rows, id: \.self){i in
                        HStack(spacing:0){
                            Spacer()
                            StoryCell(story: self.storyList[i*3])
                            if i*3+1<self.total {
                                StoryCell(story: self.storyList[i*3+1])
                                if i*3+2<self.total {
                                    StoryCell(story: self.storyList[i*3+2])
                                }
                                else{
                                    Text("")
                                        .frame(width: UIScreen.main.bounds.width/3,height:UIScreen.main.bounds.width/3)
                                }
                            }
                            else{
                                Text("")
                                    .frame(width: UIScreen.main.bounds.width/3*2,height:UIScreen.main.bounds.width/3)
                            }
                            Spacer()
                        }
                    }
                }
                Spacer()
            }
            .navigationBarTitle("스토리",displayMode: .inline)
        //}
        .sheet(isPresented: $writeVisible){
            WriteStoryView(showing: self.$writeVisible)
        }
        .onAppear(){
            HttpService.shared.getStoryImageReq(userId: "ksh"){ (storyModelArray) -> Void in
                self.storyList=storyModelArray
                
                self.total=storyModelArray.count
                self.rows=storyModelArray.count/3
                if storyModelArray.count%3 != 0 {
                    self.rows+=1
                }
            }
            
            HttpService.shared.getMyStoryImage(userId: "ksh") { (myStoryImageModel) -> Void in
                KingfisherManager.shared.retrieveImage(with: URL(string: myStoryImageModel.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                    self.myStoryImage=image!
                })
            }
        }
    }
}

struct StoryListView_Previews: PreviewProvider {
    static var previews: some View {
        StoryListView()
    }
}

struct StoryCell : View{
    
    @State var story : StoryModel
    
    @State var uiImage = UIImage()
    
    @State var storyVisible=false
    
    var body: some View {
        Image(uiImage: self.uiImage)
            .resizable()
            .frame(width: UIScreen.main.bounds.width/3, height: UIScreen.main.bounds.width/3)
            .onTapGesture {
                self.storyVisible=true
        }
        .sheet(isPresented: $storyVisible){
            StoryView(story:self.story)
        }
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.story.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
        }
    }
}
