//
//  HttpAuth.swift
//  IOS
//
//  Created by 김세현 on 2020/06/23.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation
import SwiftUI
import Combine

public class HttpService:ObservableObject{
    
    static let shared=HttpService()
    
    var ip="http://18.217.130.157:3000"
    
    func getViewCountReq(userId : String, callback: @escaping (ViewCountModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/get/count/view") else {
            print("getViewCountReq() url error")
            return
        }
        
        let data:[String:String] = ["user_id":userId]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data else { return }
            
            let decoded = try! JSONDecoder().decode(ViewCountModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func getExpressionCountReq(userId: String, callback: @escaping (ExpressionCountModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/get/count") else {
            print("getExpressionCountReq() url error")
            return
        }
        
        let data:[String:String] = ["user_id":userId]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else { return }
            
            let decoded = try! JSONDecoder().decode(ExpressionCountModel.self, from: data)
            callback(decoded)
        }.resume()
    }
}

