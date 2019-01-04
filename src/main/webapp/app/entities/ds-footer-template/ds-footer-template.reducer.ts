import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDSFooterTemplate, defaultValue } from 'app/shared/model/ds-footer-template.model';

export const ACTION_TYPES = {
  FETCH_DSFOOTERTEMPLATE_LIST: 'dSFooterTemplate/FETCH_DSFOOTERTEMPLATE_LIST',
  FETCH_DSFOOTERTEMPLATE: 'dSFooterTemplate/FETCH_DSFOOTERTEMPLATE',
  CREATE_DSFOOTERTEMPLATE: 'dSFooterTemplate/CREATE_DSFOOTERTEMPLATE',
  UPDATE_DSFOOTERTEMPLATE: 'dSFooterTemplate/UPDATE_DSFOOTERTEMPLATE',
  DELETE_DSFOOTERTEMPLATE: 'dSFooterTemplate/DELETE_DSFOOTERTEMPLATE',
  SET_BLOB: 'dSFooterTemplate/SET_BLOB',
  RESET: 'dSFooterTemplate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDSFooterTemplate>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DSFooterTemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: DSFooterTemplateState = initialState, action): DSFooterTemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DSFOOTERTEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DSFOOTERTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DSFOOTERTEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_DSFOOTERTEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_DSFOOTERTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DSFOOTERTEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DSFOOTERTEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_DSFOOTERTEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_DSFOOTERTEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_DSFOOTERTEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DSFOOTERTEMPLATE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DSFOOTERTEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DSFOOTERTEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_DSFOOTERTEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DSFOOTERTEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/ds-footer-templates';

// Actions

export const getEntities: ICrudGetAllAction<IDSFooterTemplate> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DSFOOTERTEMPLATE_LIST,
    payload: axios.get<IDSFooterTemplate>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDSFooterTemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DSFOOTERTEMPLATE,
    payload: axios.get<IDSFooterTemplate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDSFooterTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DSFOOTERTEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IDSFooterTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DSFOOTERTEMPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDSFooterTemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DSFOOTERTEMPLATE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
