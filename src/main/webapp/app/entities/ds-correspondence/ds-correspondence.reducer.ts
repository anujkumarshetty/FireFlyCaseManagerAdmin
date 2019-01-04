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

import { IDSCorrespondence, defaultValue } from 'app/shared/model/ds-correspondence.model';

export const ACTION_TYPES = {
  FETCH_DSCORRESPONDENCE_LIST: 'dSCorrespondence/FETCH_DSCORRESPONDENCE_LIST',
  FETCH_DSCORRESPONDENCE: 'dSCorrespondence/FETCH_DSCORRESPONDENCE',
  CREATE_DSCORRESPONDENCE: 'dSCorrespondence/CREATE_DSCORRESPONDENCE',
  UPDATE_DSCORRESPONDENCE: 'dSCorrespondence/UPDATE_DSCORRESPONDENCE',
  DELETE_DSCORRESPONDENCE: 'dSCorrespondence/DELETE_DSCORRESPONDENCE',
  SET_BLOB: 'dSCorrespondence/SET_BLOB',
  RESET: 'dSCorrespondence/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDSCorrespondence>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DSCorrespondenceState = Readonly<typeof initialState>;

// Reducer

export default (state: DSCorrespondenceState = initialState, action): DSCorrespondenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DSCORRESPONDENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DSCORRESPONDENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DSCORRESPONDENCE):
    case REQUEST(ACTION_TYPES.UPDATE_DSCORRESPONDENCE):
    case REQUEST(ACTION_TYPES.DELETE_DSCORRESPONDENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DSCORRESPONDENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DSCORRESPONDENCE):
    case FAILURE(ACTION_TYPES.CREATE_DSCORRESPONDENCE):
    case FAILURE(ACTION_TYPES.UPDATE_DSCORRESPONDENCE):
    case FAILURE(ACTION_TYPES.DELETE_DSCORRESPONDENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DSCORRESPONDENCE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DSCORRESPONDENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DSCORRESPONDENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_DSCORRESPONDENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DSCORRESPONDENCE):
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

const apiUrl = 'api/ds-correspondences';

// Actions

export const getEntities: ICrudGetAllAction<IDSCorrespondence> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DSCORRESPONDENCE_LIST,
    payload: axios.get<IDSCorrespondence>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDSCorrespondence> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DSCORRESPONDENCE,
    payload: axios.get<IDSCorrespondence>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDSCorrespondence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DSCORRESPONDENCE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IDSCorrespondence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DSCORRESPONDENCE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDSCorrespondence> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DSCORRESPONDENCE,
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
