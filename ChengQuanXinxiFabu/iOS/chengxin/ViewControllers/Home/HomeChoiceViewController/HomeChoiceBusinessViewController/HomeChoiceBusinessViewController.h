//
//  HomeChoiceBusinessViewController.h
//  chengxin
//
//  Created by seniorcoder on 10/30/17.
//  Copyright © 2017 chengxin. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomeChoiceBusinessViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>
{
}
@property (nonatomic, strong) IBOutlet UITableView *businessTableView;

@end