import { Component, OnInit } from '@angular/core';
import {Item} from "../item";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ItemService} from "../item.service";
import {Observable} from "rxjs";
import {TypeService} from "../type.service";
import {Type} from "../type";

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})
export class ItemEditComponent implements OnInit {
  item: Item = new Item();
  types: Observable<Type[]>;
  itemForm: FormGroup;
  myReader: FileReader = new FileReader();
  param: string;
  rightTypeFile: boolean;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private itemService: ItemService,
              private typeService: TypeService,
              private fb: FormBuilder) {
    this.param = 'id';
    this.createForm();
  }

  getItemDetails(id) {
    this.itemService.getItem(id).subscribe(
      data => {
        this.item = data;
        console.log(data);
      },
      error => console.log(error));
  }

  createForm() {
    this.itemForm = this.fb.group({
      typeId: ['', Validators.required ],
      name: ['', Validators.required,  Validators.maxLength(20)],
      cost: ['', [Validators.required, Validators.pattern('^[0-9]*[.,]?[0-9]+$')] ],
      size: ['', [Validators.required, Validators.pattern('^[0-9]*[.,]?[0-9]+$')] ],
      dislocation: ['', [Validators.required, Validators.maxLength(100)] ],
      description: ['', [Validators.required, Validators.maxLength(255) ]]
    });
  }

  ngOnInit(): void {
    this.rightTypeFile = true;
    this.types = this.typeService.getTypeList();
    this.getItemDetails(this.route.snapshot.params[this.param]);
  }

  updateItem() {
    this.itemService.updateItem(this.route.snapshot.params[this.param], this.item)
      .subscribe(data => {
        this.router.navigate(['items']);
      }, error => console.log(error));
  }

  handleImageInput(files: FileList) {
    this.rightTypeFile = (files.item(0).type === 'image/jpeg');
    this.myReader.onloadend = (e) => {
      this.item.image = this.myReader.result.toString().slice(23);
    };
    this.myReader.readAsDataURL(files.item(0));
  }

}
