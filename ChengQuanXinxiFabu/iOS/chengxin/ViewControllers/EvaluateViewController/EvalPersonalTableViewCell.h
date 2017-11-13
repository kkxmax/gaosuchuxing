//
//  EvalPersonalTableViewCell.h
//  chengxin
//
//  Created by seniorcoder on 10/25/17.
//  Copyright © 2017 chengxin. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EvalPersonalTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *imgPhoto;
@property (weak, nonatomic) IBOutlet UIImageView *imgType;
@property (weak, nonatomic) IBOutlet UILabel *lblRatio;
@property (weak, nonatomic) IBOutlet UILabel *lblEval;
@property (weak, nonatomic) IBOutlet UILabel *lblFrontEval;
@property (weak, nonatomic) IBOutlet UILabel *lblBackEval;

@end
