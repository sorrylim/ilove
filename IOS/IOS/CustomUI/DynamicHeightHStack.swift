//
//  DynamicHeightHStack.swift
//  IOS
//
//  Created by 김세현 on 2020/07/28.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct DynamicHeightHStack: View {
    @State var items : [String.SubSequence]

    var body: some View {
        GeometryReader { geometry in
            self.generateContent(in: geometry)
        }
    }

    private func generateContent(in g: GeometryProxy) -> some View {
        var width = CGFloat.zero
        var height = CGFloat.zero

        return ZStack(alignment: .topLeading) {
            ForEach(self.items, id: \.self) { item in
                self.createItem(for: String(item))
                    .padding([.horizontal, .vertical], 4)
                    .alignmentGuide(.leading, computeValue: { d in
                        if (abs(width - d.width) > g.size.width)
                        {
                            width = 0
                            height -= d.height
                        }
                        let result = width
                        if item == self.items.last! {
                            width = 0 //last item
                        } else {
                            width -= d.width
                        }
                        return result
                    })
                    .alignmentGuide(.top, computeValue: {d in
                        let result = height
                        if item == self.items.last! {
                            height = 0 // last item
                        }
                        return result
                    })
            }
        }
    }

    func createItem(for text: String) -> some View {
        Text(text)
            .padding(.all, 5)
            .font(.system(size: 15))
            .background(Color(red: 255/255, green: 160/255, blue: 0))
            .foregroundColor(Color.white)
            .cornerRadius(15)
    }
}
