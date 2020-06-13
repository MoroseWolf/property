import { Component, OnInit } from '@angular/core';
import {Item} from "../item";
import {ActivatedRoute, Router} from "@angular/router";
import {ItemService} from "../item.service";

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {
  item: Item = new Item();
  param: string;
  roles: string;
  currentUserId: number;
  flagOfEditAndDelete: boolean;

  isAnswer: boolean;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private itemService: ItemService,
              private authService: AuthService) { }

  getItemDetails(id) {
    this.itemService.getItem(id).subscribe(
      data => {
        this.item = data;
        console.log(data);
        this.checkEditRight();
      },
      error => console.log(error));
  }


  checkEditRight() {
    if (this.roles.indexOf('ADMIN') !== -1 || this.currentUserId === this.item.ownerId) {
      console.log(this.roles.indexOf('ADMIN'));
      this.flagOfEditAndDelete = true;
    }
  }

  ngOnInit() {
    this.param = 'id';
    this.flagOfEditAndDelete = false;
    this.getItemDetails(this.route.snapshot.params[this.param]);
    this.authService.getUserInfo().subscribe(
      data => {
        this.roles = data.roles;
        this.currentUserId = data.id;
        this.checkEditRight();
      });
    this.isAnswer = false;
  }

  deleteItem(id: number) {
    this.itemService.deleteItem(id)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['items']);
        },
        error => console.log(error));
  }

}
