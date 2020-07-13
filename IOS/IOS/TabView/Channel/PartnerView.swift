//
//  PartnerView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/24.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct PartnerView : View {
    
    @State var partnerList:[PartnerModel]=[]
    @State var visitPartnerList:[VisitPartnerModel]=[]
    @State var type=""
    
    
    @State var title:String
    
    var body: some View{
        List{
            if title=="내 프로필을 확인한 사람" || title=="내 스토리를 확인한 사람"{
                ForEach(visitPartnerList, id: \.user_id){partner in
                    VisitPartnerRow(partner: partner)
                }
            }
            else {
                if title.contains("내가") || title.contains("서로"){
                    ForEach(partnerList, id: \.user_id){partner in
                        PartnerRow(partner: partner)
                    }
                    .onDelete(perform : delete)
                }
                else {
                    ForEach(partnerList, id: \.user_id){partner in
                        PartnerRow(partner: partner)
                    }
                }
            }
        }
        .navigationBarTitle("\(self.title)",displayMode: .inline)
        .onAppear{
            switch self.title {
            case "내 프로필을 확인한 사람":
                self.type="profile"
                HttpService.shared.getVisitUserReq(userId: "ksh", visitType: "profile") { (partnerModelArray) -> Void in
                    self.visitPartnerList=partnerModelArray
                }
            case "내 스토리를 확인한 사람":
                self.type="story"
                HttpService.shared.getVisitUserReq(userId: "ksh", visitType: "story") { ( partnerModelArray) -> Void in
                    self.visitPartnerList=partnerModelArray
                }
            case "내가 좋아요를 보낸 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나에게 좋아요를 보낸 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 좋아요가 연결된 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType: "each1") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                    HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType: "each2") { (partnerModelArray2) -> Void in
                        self.partnerList+=partnerModelArray2
                    }
                }
            case "내가 만나고 싶은 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나를 만나고 싶어하는 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 연락처를 주고받은 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType: "each1") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                    HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType: "each2") { (partnerModelArray2) -> Void in
                        self.partnerList+=partnerModelArray2
                    }
                }
            default:
                print("default")
            }
        }
    }
    
    func delete(at offsets: IndexSet){
        if type=="story" || type=="profile" {
            return;
        }
        if let first = offsets.first {
            HttpService.shared.deleteExpressionReq(userId: "ksh", partnerId: partnerList[first].user_id, expressionType: "like") { (resultModel) -> Void in
                if resultModel.result=="success" {
                    self.partnerList.remove(at: first)
                }
            }
        }
    }
}

struct PartnerRow : View{
    
    @State var partner : PartnerModel
    @State var likeImage = Image(systemName: "heart")
    @State var meetImage = Image("call_icon")
    
    @State var uiImage=UIImage()
    
    @State var age=0
    
    var body: some View {
        VStack{
            Text(partner.expression_date)
                .font(.system(size:10))
            HStack(spacing: 20){
                
                
                Image(uiImage: self.uiImage)
                    .resizable()
                    .frame(width: 70, height: 70)
                    .cornerRadius(10)
                
                VStack(alignment: .leading){
                    Text(partner.user_nickname)
                        .font(.system(size:20,weight:.bold))
                    Spacer()
                    HStack(spacing: 10){
                        Text("\(self.age), \(partner.user_city)")
                            .font(.system(size:15))
                        Spacer()
                        meetImage
                            .resizable()
                            .frame(width: 15, height: 15)
                        likeImage
                            .foregroundColor(Color.red)
                            .frame(width: 15, height: 15)
                            .onTapGesture {
                                if self.partner.like==0 {
                                    let now = Date()
                                    let dateFormatter = DateFormatter()
                                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                    let date=dateFormatter.string(from: now)
                                    
                                    HttpService.shared.insertExpressionReq(userId: "ksh", partnerId: self.partner.user_id, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                                        if resultModel.result=="success" {
                                            self.likeImage=Image(systemName: "heart.fill")
                                        }
                                        else if resultModel.result=="eachsuccess" {
                                            self.likeImage=Image(systemName: "heart.fill")
                                        }
                                        self.partner.like=1
                                    }
                                }
                        }
                    }
                }
            }
            .padding(10)
        }
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.partner.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
            
            if self.partner.like == 1 {
                self.likeImage=Image(systemName: "heart.fill")
            }
            else {
                self.likeImage=Image(systemName: "heart")
            }
            
            let now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(self.partner.user_birthday.split(separator: "-")[0]).unsafelyUnwrapped+1)
        }
    }
}

struct VisitPartnerRow : View{
    @State var partner : VisitPartnerModel
    
    @State var likeImage = Image(systemName: "heart")
    @State var meetImage = Image("call_icon")
    
    @State var uiImage = UIImage()
    
    @State var age=0
    
    var body: some View {
        VStack{
            Text(partner.visit_date)
                .font(.system(size:10))
            HStack(spacing: 20){
                
                Image(uiImage: self.uiImage)
                    .resizable()
                    .frame(width: 70, height: 70)
                    .cornerRadius(10)
                
                VStack(alignment: .leading){
                    Text(partner.user_nickname)
                        .font(.system(size:20,weight:.bold))
                    Spacer()
                    HStack(spacing:10){
                        Text("\(self.age), \(partner.user_city)")
                            .font(.system(size:15))
                        Spacer()
                        meetImage
                            .resizable()
                            .frame(width: 15, height: 15)
                        likeImage
                            .foregroundColor(Color.red)
                            .frame(width: 15, height: 15)
                            .onTapGesture {
                                if self.partner.like==0 {
                                    let now = Date()
                                    let dateFormatter = DateFormatter()
                                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                    let date=dateFormatter.string(from: now)
                                    
                                    HttpService.shared.insertExpressionReq(userId: "ksh", partnerId: self.partner.user_id, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                                        if resultModel.result=="success" {
                                            self.likeImage=Image(systemName: "heart.fill")
                                        }
                                        else if resultModel.result=="eachsuccess" {
                                            self.likeImage=Image(systemName: "heart.fill")
                                        }
                                        self.partner.like=1
                                    }
                                }
                        }
                    }
                }
                
                
            }
            .padding(10)
        }
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.partner.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
            
            if self.partner.like == 1 {
                self.likeImage=Image(systemName: "heart.fill")
            }
            else {
                self.likeImage=Image(systemName: "heart")
            }
            
            let now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(self.partner.user_birthday.split(separator: "-")[0]).unsafelyUnwrapped+1)
        }
    }
}
