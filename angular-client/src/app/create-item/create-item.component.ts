import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Item} from "../item";
import {Type} from "../type";
import {ItemService} from "../item.service";
import {TypeService} from "../type.service";

@Component({
  selector: 'app-create-item',
  templateUrl: './create-item.component.html',
  styleUrls: ['./create-item.component.css']
})
export class CreateItemComponent implements OnInit {

  item: Item = new Item();
  types: Observable<Type[]>;
  itemForm: FormGroup;
  submitted = false;
  imageSelected = false;
  myReader: FileReader = new FileReader();
  rightTypeFile: boolean;

  constructor(private itemService: ItemService,
              private typeService: TypeService,
              private fb: FormBuilder,
              private router: Router) {

    this.createForm();
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
    this.types = this.typeService.getTypeList();
  }

  save() {
    console.log(this.item);
    this.itemService.createItem(this.item).subscribe(
      data => {
        this.item = new Item();
        this.item = data;
        this.item = new Item();
        this.router.navigate(['items']);
      },
      error => console.log(error));
  }

  onSubmit() {
    this.save();
  }

  handleImageInput(files: FileList) {
    this.rightTypeFile = (files.item(0).type === 'image/jpeg');
    this.myReader.onloadend = (e) => {
      this.item.image = this.myReader.result.toString().slice(23);
    };
    this.myReader.readAsDataURL(files.item(0));
    this.imageSelected = true;
  }

}
