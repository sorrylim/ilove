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
    
    
    
    //------------------------------Channel------------------------------//
    func getPartnerReq(userId: String, expressionType: String,sendType: String, callback: @escaping ([PartnerModel]) -> Void){
        guard let url=URL(string: "\(ip)/expression/get/\(sendType)/user") else{
            return
        }
        
        let data=[
            [
                "user_id":userId,
                "expression_type":expressionType
            ]
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode([PartnerModel].self, from: data)
            callback(decoded)
            
            
        }.resume()
    }
    
    func getVisitUserReq(userId: String, visitType: String , callback: @escaping ([PartnerModel]) -> Void){
        guard let url=URL(string: "\(ip)/expression/get/visit/user") else{
            return
        }
        
        let data=[
            [
                "user_id":userId,
                "visit_type":visitType
            ]
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode([PartnerModel].self, from: data)
            callback(decoded)
            
        }.resume()
    }
    
    func getViewCountReq(userId : String, callback: @escaping (ViewCountModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/get/count/view") else {
            return
        }
        
        let data = [
            "user_id":userId
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode(ViewCountModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func getExpressionCountReq(userId: String, callback: @escaping (ExpressionCountModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/get/count") else {
            return
        }
        
        let data = [
            "user_id":userId
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode(ExpressionCountModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    //------------------------------Channel------------------------------//
    
    
    
    //------------------------------List------------------------------//
    func getUserListReq(gender: String, userId: String, callback: @escaping ([UserModel]) -> Void){
        guard let url=URL(string: "\(ip)/user/get/list") else {
            return
        }
        
        let data=[
            [
                "user_gender" : gender,
                "user_id" : userId
            ]
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode([UserModel].self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func deleteExpressionReq(userId: String, partnerId: String, expressionType: String, callback: @escaping (ResultModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/delete") else {
            return
        }
        
        let data=[
            "user_id" : userId,
            "partner_id" : partnerId,
            "expression_type" : expressionType
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else{
                return
            }
            
            let decoded = try! JSONDecoder().decode(ResultModel.self, from : data)
            callback(decoded)
        }.resume()
    }
    
    func insertExpressionReq(userId: String, partnerId: String, expressionType: String, expressionDate: String, callback: @escaping (ResultModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/insert") else {
            return
        }
        
        let data=[
            "user_id" : userId,
            "partner_id" : partnerId,
            "expression_type" : expressionType,
            "expression_date" : expressionDate
        ]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var requset=URLRequest(url: url)
        requset.httpMethod="POST"
        requset.httpBody=body
        requset.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: requset) { (data, response, error) in
            guard let data = data else{
                return
            }
            
            let decoded = try! JSONDecoder().decode(ResultModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    //------------------------------List------------------------------//
    
    
    
}

