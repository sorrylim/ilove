//
//  CandyShopView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/10.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct CandyShopView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var candyList = [
        CandyRow(count: 10, cost: 0),
        CandyRow(count: 20, cost: 0),
        CandyRow(count: 30, cost: 0),
        CandyRow(count: 50, cost: 0),
        CandyRow(count: 100, cost: 0),
        CandyRow(count: 200, cost: 0),
        CandyRow(count: 400, cost: 0)
    ]
    
    @State var myCandy = UserInfo.shared.CANDYCOUNT
    
    var body: some View {
        VStack(spacing: 15){
            HStack{
                Text("내 사탕")
                    .font(.system(size: 10))
                Text("\(myCandy)개")
                    .font(.system(size: 15))
                    .foregroundColor(Color(red:255/255, green:160/255, blue:0/255))
            }
            HStack{
                Text("사탕 구매")
                    .font(.system(size: 10))
                    .foregroundColor(.gray)
                Spacer()
            }
            
            ForEach(candyList, id:\.count){ row in
                row
            }
            
            Spacer()
        }
        .padding()
    }
}

struct CandyRow : View {
    
    static var selected : CandyRow?
    
    var count : Int
    var cost : Int
    
    @State var didTap = false
    @State var alertBuyVisible = false
    
    var body: some View{
        HStack{
            Image(didTap == true ? "fullcandy_icon" : "candy_icon")
                .resizable()
                .frame(width:20, height: 20)
            Text("\(count)개")
            Spacer()
            Text("\(cost)원")
        }
        .padding()
        .cornerRadius(15)
        .overlay(RoundedRectangle(cornerRadius: 15)
        .stroke(self.didTap ? Color(red: 255/255, green: 160/255, blue: 0) : Color(red: 210/255, green: 210/255, blue: 210/255, opacity: 0.5), lineWidth: 1))
        .onTapGesture {
            self.didTap.toggle()
            if self.didTap == true {
                if CandyRow.selected != nil {
                    CandyRow.selected!.didTap = false
                }
                CandyRow.selected=self
            }
            else {
                CandyRow.selected=nil
            }
            self.alertBuyVisible=true
        }
        .alert(isPresented: self.$alertBuyVisible){
            Alert(title: Text("상품구매").font(.system(size: 15))
                , message: Text("\n사탕 \(self.count)개를 선택하셨습니다.\n구매하시겠습니까?").font(.system(size:12)).foregroundColor(.gray)
                , primaryButton: .destructive(Text("취소"),action: {
                    
                })
                ,secondaryButton: .cancel(Text("확인"), action: {
                    let now = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                    let date=dateFormatter.string(from: now)
                    
                    HttpService.shared.updateCandyReq(userId: UserInfo.shared.ID, count: self.count, type: "increase"){
                        UserInfo.shared.CANDYCOUNT += self.count
                    }
                })
            )
        }
    }
}
