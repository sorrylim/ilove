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
    
    @State var alertUserId = ""
    @State var alertUserNickname = ""
    @State var alertUserAge = 0
    @State var alertUserCity = ""
    @State var alertUiImage = UIImage()
    
    @State var likeAlertVisible = false
    @State var meetAlertVisible = false
    
    var body: some View{
        ZStack{
            VStack{
                HStack{
                    Text("방문 내역은")
                        .font(.system(size: 15))
                    Text("일주일")
                        .font(.system(size: 15))
                        .foregroundColor(Color.red)
                    Text("동안 보관됩니다.")
                        .font(.system(size: 15))
                }
                .padding(.top)
                List{
                    if title=="내 프로필을 확인한 사람" || title=="내 스토리를 확인한 사람"{
                        ForEach(visitPartnerList, id: \.user_id){partner in
                            VisitPartnerRow(partner: partner,view: self)
                        }
                    }
                    else {
                        if title.contains("내가") {
                            ForEach(partnerList, id: \.user_id){partner in
                                PartnerRow(partner: partner, view: self)
                            }
                            .onDelete(perform : delete)
                        }
                        else if title.contains("서로") || title.contains("이제") {
                            ForEach(partnerList, id: \.user_id){partner in
                                EachPartnerRow(partner: partner, view: self, isLike: self.title.contains("좋아요") ? true : false)
                            }
                        }
                        else {
                            ForEach(partnerList, id: \.user_id){partner in
                                PartnerRow(partner: partner, view: self)
                            }
                        }
                    }
                }
                .onAppear(){
                    UITableView.appearance().separatorStyle = .none
                }
            }
            if likeAlertVisible {
                GeometryReader{_ in
                    EachLikeAlert(userId: self.alertUserId, userNickname: self.alertUserNickname, userAge: self.alertUserAge, userCity: self.alertUserCity,uiImage: self.alertUiImage, showing: self.$likeAlertVisible)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
            if meetAlertVisible {
                GeometryReader{_ in
                    EachMeetAlert(userId: self.alertUserId, userNickname: self.alertUserNickname, userAge: self.alertUserAge, userCity: self.alertUserCity,uiImage: self.alertUiImage, showing: self.$meetAlertVisible)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
        .navigationBarTitle("\(self.title)",displayMode: .inline)
        .onAppear{
            switch self.title {
            case "내 프로필을 확인한 사람":
                self.type="profile"
                HttpService.shared.getVisitUserReq(userId: UserInfo.shared.ID, visitType: "profile") { (partnerModelArray) -> Void in
                    self.visitPartnerList=partnerModelArray
                }
            case "내 스토리를 확인한 사람":
                self.type="story"
                HttpService.shared.getVisitUserReq(userId: UserInfo.shared.ID, visitType: "story") { ( partnerModelArray) -> Void in
                    self.visitPartnerList=partnerModelArray
                }
            case "내가 좋아요를 보낸 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "like", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나에게 좋아요를 보낸 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "like", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 좋아요가 연결된 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "like", sendType: "each1") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                    HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "like", sendType: "each2") { (partnerModelArray2) -> Void in
                        self.partnerList+=partnerModelArray2
                    }
                }
            case "내가 만나고 싶은 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "meet", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나를 만나고 싶어하는 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "meet", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "이제 만날수 있는 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "meet", sendType: "each1") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                    HttpService.shared.getPartnerReq(userId: UserInfo.shared.ID, expressionType: "meet", sendType: "each2") { (partnerModelArray2) -> Void in
                        self.partnerList+=partnerModelArray2
                    }
                }
            default:
                print("default")
            }
        }
    }
    
    
    mutating func setVisible(userId: String, userNickname: String, userAge: Int, userCity: String,uiImage:UIImage, alertVisible: Bool, type:String){
        self.alertUserId=userId
        self.alertUserNickname=userNickname
        self.alertUserAge=userAge
        self.alertUserCity=userCity
        self.alertUiImage=uiImage
        if type=="like" {
            self.likeAlertVisible=alertVisible
        }
        else {
            self.meetAlertVisible=alertVisible
        }
    }
    
    func delete(at offsets: IndexSet){
        if type=="story" || type=="profile" {
            return;
        }
        if let first = offsets.first {
            if self.title == "내가 좋아요를 보낸 이성" || self.title == "서로 좋아요가 연결된 이성"{
                HttpService.shared.deleteExpressionReq(userId: UserInfo.shared.ID, partnerId: partnerList[first].user_id, expressionType: "like") { (resultModel) -> Void in
                    if resultModel.result=="success" {
                        self.partnerList.remove(at: first)
                    }
                }
            }
            else {
                HttpService.shared.deleteExpressionReq(userId: UserInfo.shared.ID, partnerId: partnerList[first].user_id, expressionType: "meet") { (resultModel) -> Void in
                    if resultModel.result=="success" {
                        self.partnerList.remove(at: first)
                    }
                }
            }
        }
    }
}

struct PartnerRow : View{
    
    @State var partner : PartnerModel
    @State var view : PartnerView
    
    @State var likeImage = Image(systemName: "heart")
    @State var meetImage = Image("call_n_icon")
    
    @State var uiImage=UIImage()
    
    @State var age=0
    @State var time=""
    @State var expressionDate=""
    
    var body: some View {
        VStack{
            Text(expressionDate)
                .font(.system(size:10))
            HStack(spacing: 20){
                Image(uiImage: self.uiImage)
                    .resizable()
                    .frame(width: 70, height: 70)
                    .clipShape(Circle())
                
                VStack(alignment: .leading){
                    Spacer()
                    Text("\(partner.user_nickname), \(self.age)")
                        .font(.system(size:18))
                        .lineLimit(2)
                    Spacer()
                    HStack(spacing: 10){
                        Text("\(self.time)")
                            .font(.system(size:15))
                        Spacer()
                        meetImage
                            .resizable()
                            .frame(width: 15, height: 15)
                            .onTapGesture {
                                if self.partner.meet==0{
                                    let now = Date()
                                    let dateFormatter = DateFormatter()
                                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                    let date=dateFormatter.string(from: now)
                                    
                                    HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.partner.user_id, expressionType: "meet", expressionDate: date) { (resultModel) -> Void in
                                        self.partner.meet=1
                                        if resultModel.result=="success" {
                                            self.meetImage=Image("call_icon")
                                        }
                                        else if resultModel.result=="eachsuccess" {
                                            self.meetImage=Image("call_icon")
                                            self.view.setVisible(userId: self.partner.user_id, userNickname: self.partner.user_nickname, userAge: self.age, userCity: self.partner.user_city, uiImage: self.uiImage, alertVisible: true, type: "meet")
                                        }
                                    }
                                }
                        }
                        likeImage
                            .frame(width: 15, height: 15)
                            .foregroundColor(self.partner.like==1 ? Color(red:255/255, green:160/255, blue:0) : .gray)
                            .onTapGesture {
                                if self.partner.like==0 {
                                    let now = Date()
                                    let dateFormatter = DateFormatter()
                                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                    let date=dateFormatter.string(from: now)
                                    
                                    HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.partner.user_id, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                                        if resultModel.result=="success" {
                                            self.likeImage=Image(systemName: "heart.fill")
                                        }
                                        else if resultModel.result=="eachsuccess" {
                                            self.likeImage=Image(systemName: "heart.fill")
                                            self.view.setVisible(userId: self.partner.user_id, userNickname: self.partner.user_nickname, userAge: self.age, userCity: self.partner.user_city, uiImage: self.uiImage, alertVisible: true, type: "like")
                                        }
                                        self.partner.like=1
                                    }
                                }
                        }
                    }
                    Spacer()
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
            
            if self.partner.meet==1 {
                self.meetImage=Image("call_icon")
            }
            else {
                self.meetImage=Image("call_n_icon")
            }
            
            self.expressionDate=String(self.partner.expression_date.split(separator: " ")[0])
            
            var now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            var birthday=self.partner.user_birthday
            var start=birthday.index(birthday.startIndex,offsetBy: 0)
            var end=birthday.index(birthday.startIndex, offsetBy: 3)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(birthday[start...end]).unsafelyUnwrapped+1)
            
            var partnerRecentTime = dateFormatter.date(from : self.partner.user_recenttime)?.addingTimeInterval(3600 * 9)
            now=now.addingTimeInterval(3600 * 9)
            
            var timeInterval = Int(now.timeIntervalSince(partnerRecentTime!))
            
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
        }
    }
}

struct VisitPartnerRow : View{
    @State var partner : VisitPartnerModel
    @State var view : PartnerView
    
    @State var likeImage = Image(systemName: "heart")
    @State var meetImage = Image("call_n_icon")
    
    @State var uiImage = UIImage()
    
    @State var age=0
    @State var time=""
    @State var visitDate=""
    
    var body: some View {
        VStack{
            Text(visitDate)
                .font(.system(size:10))
            HStack(spacing: 20){
                
                Image(uiImage: self.uiImage)
                    .resizable()
                    .frame(width: 70, height: 70)
                    .clipShape(Circle())
                
                VStack(alignment: .leading){
                    Spacer()
                    Text("\(partner.user_nickname), \(self.age)")
                        .font(.system(size:18))
                        .lineLimit(2)
                    Spacer()
                    HStack(spacing:10){
                        Text("\(self.time)")
                            .font(.system(size:15))
                        Spacer()
                        meetImage
                            .resizable()
                            .frame(width: 15, height: 15)
                            .foregroundColor(self.partner.meet==1 ? Color(red:255/255, green:160/255, blue:0) : .gray)
                            .onTapGesture {
                                if self.partner.meet==0{
                                    let now = Date()
                                    let dateFormatter = DateFormatter()
                                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                    let date=dateFormatter.string(from: now)
                                    
                                    HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.partner.user_id, expressionType: "meet", expressionDate: date) { (resultModel) -> Void in
                                        self.partner.meet=1
                                        if resultModel.result=="success" {
                                            self.meetImage=Image("call_icon")
                                        }
                                        else if resultModel.result=="eachsuccess" {
                                            self.meetImage=Image("call_n_icon")
                                            self.view.setVisible(userId: self.partner.user_id, userNickname: self.partner.user_nickname, userAge: self.age, userCity: self.partner.user_city, uiImage: self.uiImage, alertVisible: true, type: "meet")
                                        }
                                    }
                                }
                        }
                        likeImage
                            .frame(width: 15, height: 15)
                            .foregroundColor(self.partner.like==1 ? Color(red:255/255, green:160/255, blue:0) : .gray)
                            .onTapGesture {
                                if self.partner.like==0 {
                                    let now = Date()
                                    let dateFormatter = DateFormatter()
                                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                                    let date=dateFormatter.string(from: now)
                                    
                                    HttpService.shared.insertExpressionReq(userId: UserInfo.shared.ID, partnerId: self.partner.user_id, expressionType: "like", expressionDate: date) { (resultModel) -> Void in
                                        if resultModel.result=="success" {
                                            self.partner.like=1
                                        }
                                        else if resultModel.result=="eachsuccess" {
                                            self.partner.like=1
                                            self.view.setVisible(userId: self.partner.user_id, userNickname: self.partner.user_nickname, userAge: self.age, userCity: self.partner.user_city, uiImage: self.uiImage, alertVisible: true, type: "like")
                                        }
                                    }
                                }
                        }
                    }
                    Spacer()
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
            if self.partner.meet == 1 {
                self.meetImage=Image("call_icon")
            }
            else {
                self.meetImage=Image("call_n_icon")
            }
            
            self.visitDate=String(self.partner.visit_date.split(separator: " ")[0])
            
            var now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            var birthday=self.partner.user_birthday
            var start=birthday.index(birthday.startIndex,offsetBy: 0)
            var end=birthday.index(birthday.startIndex, offsetBy: 3)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(birthday[start...end]).unsafelyUnwrapped+1)
            
            var partnerRecentTime = dateFormatter.date(from : self.partner.user_recenttime)?.addingTimeInterval(3600 * 9)
            now=now.addingTimeInterval(3600 * 9)
            
            var timeInterval = Int(now.timeIntervalSince(partnerRecentTime!))
            
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
        }
    }
}

struct EachPartnerRow : View{
    @State var partner : PartnerModel
    @State var view : PartnerView
    
    @State var uiImage = UIImage()
    
    @State var age=0
    @State var time=""
    @State var expressionDate=""
    
    @State var isLike : Bool
    
    var body: some View {
        VStack{
            Text(expressionDate)
                .font(.system(size:10))
            HStack(spacing: 20){
                
                Image(uiImage: self.uiImage)
                    .resizable()
                    .frame(width: 70, height: 70)
                    .clipShape(Circle())
                
                VStack(alignment: .leading){
                    Spacer()
                    Text("\(partner.user_nickname), \(self.age)")
                        .font(.system(size:18))
                        .lineLimit(2)
                    Spacer()
                    HStack(spacing:10){
                        Text("\(self.time)")
                            .font(.system(size:15))
                        Spacer()
                    }
                    Spacer()
                }
                Spacer()
                Button(action: {
                    self.view.setVisible(userId: self.partner.user_id, userNickname: self.partner.user_nickname, userAge: self.age, userCity: self.partner.user_city, uiImage: self.uiImage, alertVisible: true, type: self.isLike ? "like" : "meet")
                }){
                    Text(isLike ? "대화하기" : "연락처 열람")
                    .font(.system(size: 15))
                    .foregroundColor(Color.white)
                    .padding(10)
                    .background(Color(red: 255/255, green: 160/255, blue: 0))
                    .cornerRadius(15)
                }
            }
            .padding(10)
        }
        .onAppear(){
            KingfisherManager.shared.retrieveImage(with: URL(string: self.partner.image)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
                self.uiImage=image!
            })
            
            self.expressionDate=String(self.partner.expression_date.split(separator: " ")[0])
            
            var now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            var birthday=self.partner.user_birthday
            var start=birthday.index(birthday.startIndex,offsetBy: 0)
            var end=birthday.index(birthday.startIndex, offsetBy: 3)
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(birthday[start...end]).unsafelyUnwrapped+1)
            
            var partnerRecentTime = dateFormatter.date(from : self.partner.user_recenttime)?.addingTimeInterval(3600 * 9)
            now=now.addingTimeInterval(3600 * 9)
            
            var timeInterval = Int(now.timeIntervalSince(partnerRecentTime!))
            
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
        }
    }
}
