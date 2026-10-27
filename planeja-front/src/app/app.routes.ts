import { Routes } from '@angular/router';
import { Template } from './template/template';
import { CadastroCartao } from './cartoes/cadastro-cartao/cadastro-cartao';
import { ListarCartoes } from './cartoes/listar-cartoes/listar-cartoes/listar-cartoes';
import { ListarCategorias } from './categoria/listar-categorias/listar-categorias';
import { CadastroCategorias } from './categoria/cadastro-categoria/cadastro-categorias';
import { CadastroLancamento } from './lancamento/cadastro-lancamento/cadastro-lancamento';

export const routes: Routes = [
  {
    path: '',
    component: Template,
    children: [
      {
        path: 'cadastro-cartao',
        component: CadastroCartao,
      },
      {
        path: 'listar-cartoes',
        component: ListarCartoes,
      },
      {
        path: 'listar-categorias',
        component: ListarCategorias,
      },
      {
        path: 'cadastro-categoria',
        component: CadastroCategorias,
      },
      {
        path: 'cadastro-lancamento',
        component: CadastroLancamento,
      }
    ],
  },
];
