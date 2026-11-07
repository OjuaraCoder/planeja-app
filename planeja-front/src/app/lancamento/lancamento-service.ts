import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { DadosLancamentoForm, DetalhesLancamento, FiltroLancamento } from './lancamento-dados';
import { Observable } from 'rxjs';
import { DetalhesCategoria } from '../categoria/categoria-dados';
import { DetalhesCartao } from '../cartoes/cartao-dados';
import { PageResult } from '../common/pagination/page-result';


@Injectable({
  providedIn: 'root',
})
export class LancamentoService {
  http = inject(HttpClient);
  baseURL = 'http://localhost:8080/lancamentos';

  criar(dados: DadosLancamentoForm): Observable<DetalhesLancamento> {
    return this.http.post<DetalhesLancamento>(this.baseURL, dados);
  }

  listarCategoriasDisponiveis(): Observable<DetalhesCategoria[]> {
    return this.http.get<DetalhesCategoria[]>(`${this.baseURL}/categorias-ativas`);
  }

  listarCartoesDisponiveis(): Observable<DetalhesCartao[]> {
    return this.http.get<DetalhesCartao[]>(`${this.baseURL}/cartoes-ativos`);
  }

  listarLancamentos(filtros: FiltroLancamento): Observable<PageResult<DetalhesLancamento>> {
    let params = new HttpParams();
    let page = filtros.page ?? 0;
    let size = filtros.size ?? 10;

    params = params.set('page', page).set('size', size);

    if (filtros.mes) {
      params = params.set('mes', filtros.mes);
    }
    if (filtros.tipoLancamento) {
      params = params.set('tipoLancamento', filtros.tipoLancamento);
    }
    if (filtros.categoriaId) {
      params = params.set('categoria-id', filtros.categoriaId);
    }
    if (filtros.cartaoId) {
      params = params.set('cartao-id', filtros.cartaoId);
    }

    return this.http.get<PageResult<DetalhesLancamento>>(this.baseURL, { params: params });

  }

}
