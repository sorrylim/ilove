//
//  ContentView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/18.
//  Copyright © 2020 김세현. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    @State var count=0
    
    var body: some View {
        HStack {
            NavigationView{
                Form{
                    Text("\(count)")
                    Button("asd"){
                        self.count+=1
                    }
                }.navigationBarTitle("asd")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
