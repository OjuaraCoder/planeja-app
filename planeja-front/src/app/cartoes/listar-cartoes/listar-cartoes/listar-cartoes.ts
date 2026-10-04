import { Component, OnInit, inject } from '@angular/core';
import { CartaoService } from '../../cartao-service';
import { PageResult } from '../../../common/pagination/page-result';
import { DetalhesCartao } from '../../dados-cartao';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listar-cartoes',
  imports: [CommonModule],
  templateUrl: './listar-cartoes.html',
  styleUrl: './listar-cartoes.scss',
})
export class ListarCartoes implements OnInit {
  service = inject(CartaoService);
  listagem$!: Observable<PageResult<DetalhesCartao>>;
  paginaAtual = 0;
  tamanhoPagina = 5;

  ngOnInit(): void {
    this.listarCartoes();
  }

  listarCartoes(): void {
    this.listagem$ = this.service.listarCartoes(this.paginaAtual, this.tamanhoPagina);
  }

  navegar(pagina: number): void {
    this.paginaAtual = pagina;
    this.listarCartoes();
  }

  navegarProximo(listagem: PageResult<DetalhesCartao>) {
    if (!listagem.last) {
      this.navegar(listagem.number + 1);
    }
  }

  navegarAnterior(listagem: PageResult<DetalhesCartao>) {
    if (!listagem.first) {
      this.navegar(listagem.number - 1);
    }
  }

  paginas(totalPages: number): number[]{
    return Array.from({length: totalPages}, (valor, index) => index);
  }

  registroInicial(listagem: PageResult<DetalhesCartao>) {
    if(listagem.totalElements === 0){
      return 0;
    }
    return (listagem.number * listagem.size) + 1;
  }

  registroFinal(listagem: PageResult<DetalhesCartao>) {
    if (listagem.totalElements === 0){
      return 0;
    }
    return Math.min((listagem.number + 1) * listagem.size, listagem.totalElements);
  }

}
