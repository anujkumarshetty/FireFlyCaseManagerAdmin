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

import { IDsheadertemplate, defaultValue } from 'app/shared/model/dsheadertemplate.model';

export const ACTION_TYPES = {
  FETCH_DSHEADERTEMPLATE_LIST: 'dsheadertemplate/FETCH_DSHEADERTEMPLATE_LIST',
  FETCH_DSHEADERTEMPLATE: 'dsheadertemplate/FETCH_DSHEADERTEMPLATE',
  CREATE_DSHEADERTEMPLATE: 'dsheadertemplate/CREATE_DSHEADERTEMPLATE',
  UPDATE_DSHEADERTEMPLATE: 'dsheadertemplate/UPDATE_DSHEADERTEMPLATE',
  DELETE_DSHEADERTEMPLATE: 'dsheadertemplate/DELETE_DSHEADERTEMPLATE',
  SET_BLOB: 'dsheadertemplate/SET_BLOB',
  RESET: 'dsheadertemplate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDsheadertemplate>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DsheadertemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: DsheadertemplateState = initialState, action): DsheadertemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DSHEADERTEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DSHEADERTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DSHEADERTEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_DSHEADERTEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_DSHEADERTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DSHEADERTEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DSHEADERTEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_DSHEADERTEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_DSHEADERTEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_DSHEADERTEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DSHEADERTEMPLATE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DSHEADERTEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DSHEADERTEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_DSHEADERTEMPLATE):
      console.log(action.payload.data);
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DSHEADERTEMPLATE):
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

const apiUrl = 'api/dsheadertemplates';

// Actions

export const getEntities: ICrudGetAllAction<IDsheadertemplate> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DSHEADERTEMPLATE_LIST,
    payload: axios.get<IDsheadertemplate>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDsheadertemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DSHEADERTEMPLATE,
    payload: axios.get<IDsheadertemplate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDsheadertemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DSHEADERTEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IDsheadertemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DSHEADERTEMPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDsheadertemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DSHEADERTEMPLATE,
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
