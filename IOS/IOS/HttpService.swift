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
    
    //------------------------------Common------------------------------//
    func insertHistoryReq(userId: String, partnerId: String, visitType: String, visitDate: String, callback: @escaping (ResultModel) -> Void){
        guard let url=URL(string: "\(ip)/expression/insert/history") else{
            return
        }
        
        let data=[
                "user_id":userId,
                "partner_id":partnerId,
                "visit_type":visitType,
                "visit_date":visitDate
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
            
            let decoded = try! JSONDecoder().decode(ResultModel.self, from: data)
            callback(decoded)
            
            
        }.resume()
    }
    
    func getProfileImageReq(userId : String, callback: @escaping ([ProfileImageModel]) -> Void){
        guard let url=URL(string: "\(ip)/image/get/profile") else {
            return
        }
        
        let data = [
            [
                "user_id" : userId
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
            
            let decoded = try! JSONDecoder().decode([ProfileImageModel].self, from: data)
            
            callback(decoded)
        }.resume()
        
    }
    
    func getUserOptionReq(userId : String, callback: @escaping (UserOptionModel) -> Void){
        guard let url=URL(string: "\(ip)/user/get/option") else {
            return
        }
        
        let data = [
            "user_id" : userId
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
            
            let decoded = try! JSONDecoder().decode(UserOptionModel.self, from: data)
            
            callback(decoded)
        }.resume()
    }
    //------------------------------Common------------------------------//
    
    
    
    //------------------------------Channel------------------------------//
    func getNewUserReq(userGender: String, callback: @escaping ([NewUserModel]) -> Void){
        guard let url=URL(string: "\(ip)/user/get/new/list") else{
            return
        }
        
        let data=[
            [
                "user_gender" : userGender
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
            
            let decoded = try! JSONDecoder().decode([NewUserModel].self, from: data)
            
            callback(decoded)
        }.resume()
    }
    
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
    
    func getVisitUserReq(userId: String, visitType: String , callback: @escaping ([VisitPartnerModel]) -> Void){
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
            let decoded = try! JSONDecoder().decode([VisitPartnerModel].self, from: data)
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
    
    func getUpProfileUserListReq(gender: String, userId: String, callback: @escaping ([UserModel]) -> Void){
        guard let url=URL(string: "\(ip)/user/get/upprofile/list") else {
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
    
    
    
    //------------------------------Story------------------------------//
    func getMyStoryImage(userId: String, callback: @escaping (MyStoryImageModel) -> Void){
        guard let url=URL(string: "\(ip)/image/get/my/story") else {
            return
        }
        
        let data=[
            "user_id" : userId
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
            
            let decoded = try! JSONDecoder().decode(MyStoryImageModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func getStoryImageReq(userId: String, callback: @escaping ([StoryModel]) -> Void){
        guard let url=URL(string: "\(ip)/image/get/story") else {
            return
        }
        
        let data=[
            [
                "user_id" : userId,
                "image_usage" : "story"
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
            
            let decoded = try! JSONDecoder().decode([StoryModel].self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func getStoryUserReq(userId: String, imageId:Int, callback: @escaping (StoryUserModel) -> Void){
        guard let url=URL(string: "\(ip)/image/get/story/user") else {
            return
        }
        
        let data=[
            "user_id" : userId,
            "image_id" : imageId
            ] as [String : Any]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode(StoryUserModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func insertStoryReq(userId: String, imageUrl: URL,imageUsage: String, imageContent: String, imageDate: String, uiImage: UIImage){
        guard let url=URL(string: "\(ip)/image/upload") else {
            return
        }
        
        let imageName=imageUrl.lastPathComponent
        
        let boundary = "Boundary-\(UUID().uuidString)"

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("multipart/form-data; boundary=\(boundary)", forHTTPHeaderField: "Content-Type")
        
        let httpBody = NSMutableData()

        httpBody.appendString(convertFormField(name: "user_id", value: userId, using: boundary))
        httpBody.appendString(convertFormField(name: "image_usage", value: imageUsage, using: boundary))
        httpBody.appendString(convertFormField(name: "image_content", value: imageContent, using: boundary))
        httpBody.appendString(convertFormField(name: "image_date", value: imageDate, using: boundary))
        
        
        httpBody.append(convertFileData(name: "img",
                                        fileName: imageName,
                                        mimeType: "image/jpg",
                                        fileData: uiImage.jpegData(compressionQuality: 0)!,
                                        using: boundary))
        
        httpBody.appendString("--\(boundary)--")
        
        print(uiImage.jpegData(compressionQuality: 0.5))
        
        request.httpBody = httpBody as Data
        
        URLSession.shared.dataTask(with: request) { data, response, error in
        }.resume()
 
    }
    
    func convertFormField(name: String, value: String, using boundary: String) -> String {
      var fieldString = "--\(boundary)\r\n"
      fieldString += "Content-Disposition: form-data; name=\"\(name)\"\r\n"
      fieldString += "\r\n"
      fieldString += "\(value)\r\n"

      return fieldString
    }
    
    func convertFileData(name: String, fileName: String, mimeType: String, fileData: Data, using boundary: String) -> Data {
      let data = NSMutableData()

      data.appendString("--\(boundary)\r\n")
      data.appendString("Content-Disposition: form-data; name=\"\(name)\"; filename=\"\(fileName)\"\r\n")
      data.appendString("Content-Type: \(mimeType)\r\n\r\n")
      data.append(fileData)
      data.appendString("\r\n")

      return data as Data
    }
    
    func deleteStoryExpressionReq(userId: String, imageId:Int, callback: @escaping (ResultModel) -> Void){
        guard let url=URL(string: "\(ip)/image/delete/expression") else {
            return
        }
        
        let data=[
            "user_id" : userId,
            "image_id" : imageId
            ] as [String : Any]
        
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode(ResultModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    
    func insertStoryExpressionReq(userId: String, imageId:Int,expressionDate: String, callback: @escaping (ResultModel) -> Void){
        guard let url=URL(string: "\(ip)/image/insert/expression") else {
            return
        }
        
        let data=[
            "user_id" : userId,
            "image_id" : imageId,
            "expression_date" : expressionDate
            ] as [String : Any]
        
        let body=try! JSONSerialization.data(withJSONObject: data)
        
        var request=URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else {
                return
            }
            
            let decoded = try! JSONDecoder().decode(ResultModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    //------------------------------Story------------------------------//
    
    //------------------------------Chat------------------------------//
    func createRoomReq(userId: String, userNickname: String, callback:@escaping (ChatRoomModel) -> Void){
        guard let url = URL(string: "\(ip)/image/insert/expression") else{
            return
        }
        
        let data=[
            "room_maker" : "ksh",
            "room_partner" : userId,
            "room_partner_title" : "ksh&\(userNickname)}"
        ]
        
        let body = try! JSONSerialization.data(withJSONObject: data)
        
        var request = URLRequest(url: url)
        request.httpMethod="POST"
        request.httpBody=body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.dataTask(with: request) { (data,response,error) in
            guard let data = data else{
                return
            }
            
            let decoded = try! JSONDecoder().decode(ChatRoomModel.self, from: data)
            callback(decoded)
        }.resume()
    }
    //------------------------------Chat------------------------------//
}

