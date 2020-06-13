import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {Observable, of} from 'rxjs';
import { ItemService } from '../item.service';
import {Item} from '../item';
import {Type} from '../type';
import {TypeService} from '../type.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {

  items: Observable<Item[]>;
  types: Observable<Type[]>;
  totalElements: number;
  totalPages: number;
  paginationArray: number[];
  pageSizesArray: number[] = [];
  pageNumber: number;
  pageSize: number;
  sortBy: string;
  sortFields: string[][];
  filters: string[][] = [];
  filterCost: string;
  filterType: string;
  filterOwner: string;
  listOfCosts: number[];
  listOfOwns: Array<Array<string>> = [];

  constructor(private itemService: ItemService,
              private typeService: TypeService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.totalElements = 0;
    this.totalPages = 0;
    this.pageNumber = 1;
    this.pageSize = 3;
    this.sortBy = 'id';
    this.listOfCosts = [0, 5000, 10000, 15000, 20000];
    this.listOfOwns = [['ALL', 'All advertisements'], ['MY', 'Only my advertisements']];
    this.types = this.typeService.getTypeList();
    this.setDefaultFilterValues();
    this.initPageSizesArray();
    this.initSortFields();
    this.reloadData();
  }

  initSortFields() {
    this.sortFields = [
      ['id', 'None'],
      ['typeId', 'Type'],
      ['cost', 'Cost']
    ];
  }

  initPaginationArray() {
    this.paginationArray = [];
    for (let i = 1; i <= this.totalPages; i++) {
      this.paginationArray.push(i);
    }
  }

  initPageSizesArray() {
    const start = 3;
    const step = 3;
    const max = 15;
    for (let i = start; i <= max; i += step) {
      this.pageSizesArray.push(i);
    }
  }


  setDefaultFilterValues() {
    this.filterCost = '0';
    this.filterOwner = 'ALL';
    this.filterType = 'ALL';
  }

  changeItemsOnPage() {
    this.pageNumber = 1;
    this.reloadData();
  }

  changePage(pageNumber: number) {
    this.pageNumber = pageNumber;
    this.reloadData();
  }

  changePageToNext() {
    if (this.pageNumber !== this.paginationArray.length) {
      this.pageNumber = this.pageNumber + 1;
      this.reloadData();
    }
  }

  changePageToPrev() {
    if (this.pageNumber !== 1) {
      this.pageNumber = this.pageNumber - 1;
      this.reloadData();
    }
  }

  addFilter(filterKey: string, filterValue: string) {
    const index = this.findFilterByRey(filterKey);
    if (index !== -1) {
      this.filters.splice(index, 1);
    }
    if (filterValue !== 'ALL') {
      this.filters.push([filterKey, filterValue]);
    }
  }

  deleteAllFilters() {
    this.setDefaultFilterValues();
    this.filters = [];
    this.pageNumber = 1;
    this.reloadData();
  }

  findFilterByRey(key: string): number {
    for (let i = 0; i < this.filters.length; i++) {
      if (this.filters[i][0] === key) {
        return i;
      }
    }
    return -1;
  }

  reloadData() {
    if (this.pageNumber == null) {
      this.pageNumber = 1;
    }
    this.itemService.getItemsList(this.pageNumber - 1, this.pageSize, this.sortBy, this.filters).subscribe(
      data => {
        this.items = data.items;
        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
        this.initPaginationArray();
      },
      error => {
        console.log(error);
        if (error.error.code === 401) { this.router.navigate(['/login']); }
      });
  }


}
